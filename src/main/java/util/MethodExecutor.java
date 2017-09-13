package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.*;

/**
 * Created by yepeng.huang on 2017/5/12.
 */
public class MethodExecutor<T> {

    private static final Logger log = LoggerFactory.getLogger(MethodExecutor.class);

    //当函数的参数有且只有一个,且传入的参数值为null时使用
    private static final Object[] defaultArgs = {null};

    private final Object target;

    private final Method method;

    private final int hashCode;

    public MethodExecutor(Object target, String methodName, Class<?>... parameterTypes) {
        this.target = target;

        Class<?> cls = target.getClass();
        try {
            this.method = cls.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException noSuchMethodException) {
            throw new RuntimeException(noSuchMethodException);
        }

        this.hashCode = initHashCode(target, methodName, parameterTypes);
    }

    public void execRun(CountDownLatch latch, Object... args) {
        ThreadPoolExecutor executor = ThreadPoolHolder.getThreadPool();
        WorkerRun worker = new WorkerRun(latch, this.target, this.method, args);
        executor.execute(worker);
    }

    public Future<T> execCall(CountDownLatch latch, Object... args) {
        ThreadPoolExecutor executor = ThreadPoolHolder.getThreadPool();
        WorkerCall<T> worker = new WorkerCall<T>(latch, this.target, this.method, args);

        Future<T> result = executor.submit(worker);
        return result;
    }

    private int initHashCode(Object target, String methodName, Class<?>... parameterTypes) {
        int hashCode = 1;
        hashCode = 31 * hashCode + (target == null ? 0 : target.hashCode());
        hashCode = 31 * hashCode + (methodName == null ? 0 : methodName.hashCode());
        for (Class<?> parameterType : parameterTypes)
            hashCode = 31 * hashCode + (parameterType == null ? 0 : parameterType.hashCode());

        return hashCode;
    }

    public int hashCode() {
        return hashCode;
    }

    public boolean equals(Object methodExecutor) {
        if (!MethodExecutor.class.isInstance(methodExecutor))
            return false;

        MethodExecutor<?> executor = (MethodExecutor) methodExecutor;
        return target.equals(executor.target) && method.equals(executor.method);
    }

    private static class WorkerRun implements Runnable {

        private static final Logger log = LoggerFactory.getLogger(WorkerRun.class);

        private final CountDownLatch latch;

        private final Object target;
        private final Object[] args;
        private volatile Method method;

        protected WorkerRun(CountDownLatch latch, Object target, Method method, Object... args) {
            this.latch = latch;
            this.target = target;
            this.method = method;
            this.args = args;
        }

        public void run() {
            try {
                if (this.args != null) {
                    this.method.invoke(this.target, this.args);
                } else {
                    if (this.method.getParameterCount() == 1) {
                        this.method.invoke(this.target, defaultArgs);
                    } else {
                        this.method.invoke(this.target);
                    }
                }
            } catch (InvocationTargetException invocationTargetException) {
                log.debug("InvocationTargetException:\t{} ===> {}", invocationTargetException.getMessage(), this);
                throw new RuntimeException(invocationTargetException);
            } catch (IllegalAccessException illegalAccessException) {
                log.debug("IllegalAccessException:\t{} ===> {}", illegalAccessException.getMessage(), this);
                throw new RuntimeException(illegalAccessException);
            } catch (IllegalArgumentException illegalArgumentException) {
                log.debug("IllegalArgumentException:\t{} ===> {}", illegalArgumentException.getMessage(), this);
                throw illegalArgumentException;
            } finally {
                if (latch != null) {
                    latch.countDown();
                }
            }
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{\"target\": \"").append(target).append('"');
            builder.append(", \"method\": \"").append(method).append('"');
            builder.append(", \"target\": ").append(Arrays.toString(method.getParameterTypes()));
            builder.append(", \"args\": ").append(Arrays.toString(args));
            builder.append(", \"latch\": ").append(latch == null ? "null" : latch.getCount());
            builder.append('}');

            return builder.toString();
        }
    }

    private static class WorkerCall<T> implements Callable<T> {

        private static final Logger log = LoggerFactory.getLogger(WorkerCall.class);

        private final CountDownLatch latch;

        private final Object target;
        private final Object[] args;
        private volatile Method method;

        protected WorkerCall(CountDownLatch latch, Object target, Method method, Object... args) {
            this.latch = latch;
            this.target = target;
            this.method = method;
            this.args = args;
        }

        public T call() {
            try {
                if (this.args != null) {
                    return (T) this.method.invoke(this.target, this.args);
                } else {
                    if (this.method.getParameterCount() == 1) {
                        return (T) this.method.invoke(this.target, defaultArgs);
                    } else {
                        return (T) this.method.invoke(this.target);
                    }
                }
            } catch (InvocationTargetException invocationTargetException) {
                log.debug("InvocationTargetException:\t{} ===> {}", invocationTargetException.getMessage(), this);
                throw new RuntimeException(invocationTargetException);
            } catch (IllegalAccessException illegalAccessException) {
                log.debug("IllegalAccessException:\t{} ===> {}", illegalAccessException.getMessage(), this);
                throw new RuntimeException(illegalAccessException);
            } catch (IllegalArgumentException illegalArgumentException) {
                log.debug("IllegalArgumentException:\t{} ===> {}", illegalArgumentException.getMessage(), this);
                throw illegalArgumentException;
            } finally {
                if (latch != null) {
                    latch.countDown();
                }
            }
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{\"target\": \"").append(target).append('"');
            builder.append(", \"method\": \"").append(method).append('"');
            builder.append(", \"target\": ").append(Arrays.toString(method.getParameterTypes()));
            builder.append(", \"args\": ").append(Arrays.toString(args));
            builder.append(", \"latch\": ").append(latch == null ? "null" : latch.getCount());
            builder.append('}');

            return builder.toString();
        }
    }

    private static class ThreadPoolHolder {
        private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 256, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());

        //不支持实例化
        private ThreadPoolHolder() {
            throw new UnsupportedOperationException();
        }

        public static ThreadPoolExecutor getThreadPool() {
            return executor;
        }
    }
}

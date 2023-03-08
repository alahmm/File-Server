package threadlocal;

class UseThreadLocal {
    public static ThreadLocal<Integer> makeThreadLocal(int counter) {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        threadLocal.set(counter + 1);
        return threadLocal;
        // write your code here
    }
}
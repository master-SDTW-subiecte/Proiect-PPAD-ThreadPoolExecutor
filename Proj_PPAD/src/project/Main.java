package project;

public class Main 
{
    private static final int CORE_POOL_SIZE = 3;
    private static final int MAXIMUM_POOL_SIZE = 7;
    private static final int KEEP_ALIVE_TIME = 1000;
    private static final int QUEUE_SIZE = 3;
    private static final int NUMBER_OF_TASKS = 50;
    public static long startTime = System.currentTimeMillis();

    private static void reject(int id, double end) 
    {
        System.out.println("Taskul " + id + " respins la " + end);
    }

    public static void main(String[] args) throws InterruptedException 
    {
        MyThreadPoolExecutor threadPoolExecutor = new MyThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, QUEUE_SIZE);

        for (int i = 0; i <= NUMBER_OF_TASKS; ++i) 
        {
            boolean success = false;
            while (!success) 
            {
                if (!threadPoolExecutor.execute(new MyTask(i))) 
                {
                    double end = System.currentTimeMillis() - startTime;
                    reject(i, end);
                } 
                else 
                {
                    success = true;
                }
                Thread.sleep(10);
            }
        }
        boolean threadPoolIsActive = true;
        while (threadPoolIsActive) 
        {
            if (threadPoolExecutor.getThreadPoolSize() == CORE_POOL_SIZE) 
            {
                threadPoolExecutor.destroy();
                threadPoolIsActive = false;
            }
            Thread.sleep(100);
        }
    }
}

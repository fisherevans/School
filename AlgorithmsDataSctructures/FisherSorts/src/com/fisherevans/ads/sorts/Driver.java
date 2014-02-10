package com.fisherevans.ads.sorts;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * User: David Fisher Evans
 * Date: 11/7/13
 */
public class Driver
{

    public static void main(String[] args)
    {
        int arrayCount = 10;
        int arraySize = 250000;
        int arrayElementScale = 1000;

        System.out.printf("Generating %d array(s) of %d element(s) each containing values between 0 and %d.\n\n",
                arrayCount, arraySize, arrayElementScale-1);
        int[][] arrays = generateArrays(arrayCount, arraySize, arrayElementScale);

        averageSortTime(ShellSort.class, arrays);
        averageSortTime(SelectionSort.class, arrays);

        System.out.printf("Exit\n");
    }

    public static int[][] generateArrays(int arrayCount, int arraySize, int arrayElementScale)
    {
        int[][] arrays = new int[arrayCount][arraySize];
        int arrayID, index;
        for(arrayID = 0;arrayID < arrayCount;arrayID++)
        {
            arrays[arrayID] = new int[arraySize];
            for(index = 0;index < arraySize;index++)
                arrays[arrayID][index] = (int)(Math.random()*arrayElementScale);
        }
        return arrays;
    }

    public static long averageSortTime(Class sortClass, int[][] arrays)
    {
        String className = sortClass.getName().replaceAll(".*\\.", "");
        System.out.printf("Doing timings for %s with pre-generated array...\n", className);
        try
        {
            Constructor<Sort> constructor = sortClass.getConstructor(int[].class);
            long totalTime = 0, tmpTime;
            for(int testArrayId = 0;testArrayId < arrays.length;testArrayId++)
            {
                int[] tmpArray = Arrays.copyOf(arrays[testArrayId], arrays[testArrayId].length);
                tmpTime = timeSort(constructor.newInstance(tmpArray));
                totalTime += tmpTime;
                System.out.printf("Sort %d: %dms\n", testArrayId+1, tmpTime);
            }
            long averageTime = totalTime/arrays.length;
            System.out.printf("Average %s Time: %dms/sort\n\n", className, averageTime);
            return averageTime;
        } catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public static long timeSort(Sort sort)
    {
        Timer timer = new Timer();
        timer.start();
        sort.sort();
        timer.stop();
        return timer.time();
    }
}

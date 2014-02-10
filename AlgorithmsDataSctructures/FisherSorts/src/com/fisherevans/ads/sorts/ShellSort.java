package com.fisherevans.ads.sorts;

/**
 * User: David Fisher Evans
 * Date: 11/7/13
 * Created based on answer by Adio: http://stackoverflow.com/questions/4833423/shell-sort-java-example
 * Referencing: http://www.amazon.com/dp/B006Y14OD8/?tag=stackoverfl08-20
 */
public class ShellSort extends Sort
{
    public ShellSort(int[] array)
    {
        super(array);
    }

    @Override
    public void sort()
    {
        int gap, outer, inner;
        for(gap = _array.length/2;gap > 0;gap /= 2)
        {
            for(outer = gap;outer < _array.length;outer++)
            {
                int tmp = _array[outer];
                for(inner = outer;(inner >= gap) && (tmp-_array[inner-gap] < 0);inner -= gap)
                    _array[inner] = _array[inner-gap];
                _array[inner] = tmp;
            }
        }
    }
}

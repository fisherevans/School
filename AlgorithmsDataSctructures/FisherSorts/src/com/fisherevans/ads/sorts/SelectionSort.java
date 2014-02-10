package com.fisherevans.ads.sorts;

/**
 * User: David Fisher Evans
 * Date: 11/7/13
 */
public class SelectionSort extends Sort
{
    public SelectionSort(int[] array)
    {
        super(array);
    }

    @Override
    public void sort()
    {
        int outerId, innerId, smallestId, temp;
        for(outerId = 0;outerId < _array.length-1;outerId++)
        {
            smallestId = outerId;

            for(innerId = outerId+1;innerId < _array.length;innerId++)
                if(_array[innerId] < _array[smallestId])
                    smallestId = innerId;

            temp = _array[outerId];
            _array[outerId] = _array[smallestId];
            _array[smallestId] = temp;
        }
    }
}

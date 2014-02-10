package info.fisherevans.info.vtc.oop.lab1;

public class Counters
{
	/**
	  * count the number of occurrences of value in the count elements of nums
	  * @param nums the array of numbers to search
	  * @param count the number of elements in array to consider,
	  *      must be >= 0, must be <= nums.length
	  * @param value the value being searched for in nums
	  * @return returns the number of occurrences of value in nums, always >= 0.
	  */
	public static int countValues(int[] nums, int count, int value) // CRAIG STOP DOING THESE IN THE WEE HOURS OF THE MORNING! Removed an s.
	{
		int found = 0;
		for(int index = 0;index < count;index++)
		{
			if(nums[index] == value)
				found++;
		}
		
		return found;
	}
	
	/**
	  * find the first occurrence of value in nums
	  * @param nums the array of numbers to search
	  * @param count the number of elements in array to consider,
	  *      must be >= 0, must be <= nums.length
	  * @param value the value being searched for in nums
	  * @return returns the index of the occurrence (>- 0) or -1 if not found. // CRAIG "="?
	  */
	public static int findFirst(int[] nums, int count, int value) // CRAIG STOP DOING THESE IN THE WEE HOURS OF THE MORNING! Removed an s.
	{
		for(int index = 0;index < count;index++)
		{
			if(nums[index] == value)
				return index;
		}
		
		return -1;
	}
	
	/**
	  * find the next occurrence of value in nums after after
	  * @param nums the array of numbers to search
	  * @param count the number of elements in array to consider,
	  *      must be >= 0, must be <= nums.length
	  * @param value the value being searched for in nums
	  * @param after consider only elements in nums between after+1 and count
	  * @return returns the index of the occurrence (>- 0) or -1 if not found.
	  */
	public static int findNext(int[] nums, int count, int value, int after)
	{
		for(int index = after;index < count;index++)
		{
			if(nums[index] == value)
				return index;
		}
		
		return -1;
	}
	
	/**
	  * find the last occurrence of value in nums
	  * @param nums the array of numbers to search
	  * @param count the number of elements in array to consider,
	  *      must be >= 0, must be <= nums.length
	  * @param value the value being searched for in nums
	  * @return returns the index of the occurrence (>- 0) or -1 if not found.
	  */
	public static int findLast(int[] nums, int count, int value) 
	{
		for(int index = count-1;index >= 0;index--)
		{
			if(nums[index] == value)
				return index;
		}
		
		return -1;
	}
	
	/**
	  * find the previous occurrence of value in nums before before
	  * @param nums the array of numbers to search
	  * @param count the number of elements in array to consider,
	  *      must be >= 0, must be <= nums.length
	  * @param value the value being searched for in nums
	  * @param before consider only elements in nums between before-1 and 0
	  * @return returns the index of the occurrence (>- 0) or -1 if not found.
	  */
	public static int findPrev(int[] nums, int count, int value, int before)
	{
		for(int index = before-1;index > 0;index--)
		{
			if(nums[index] == value)
				return index;
		}
		
		return -1;
	} 
	
	public static void main(String[] args)
	{
		int nums[] = { 4, 5, 6, 7, 4 };
		System.out.println("int nums[] = { 4, 5, 6, 7, 4 }");
		System.out.println();
		
		System.out.println("countValues(nums, 5, 4) Should return 2. Does return: " + countValues(nums, 5, 4));
		System.out.println("countValues(nums, 5, 8) Should return 0. Does return: " + countValues(nums, 5, 8));
		System.out.println();
		
		System.out.println("findFirst(nums, 5, 7) Should return 3. Does return: " + findFirst(nums, 5, 7));
		System.out.println("findFirst(nums, 3, 7) Should return -1. Does return: " + findFirst(nums, 3, 7));
		System.out.println();

		System.out.println("findNext(nums, 5, 4, 2) Should return 4. Does return: " + findNext(nums, 5, 4, 2));
		System.out.println("findNext(nums, 5, 5, 3) Should return -1. Does return: " + findNext(nums, 5, 5, 3));
		System.out.println();

		System.out.println("findLast(nums, 5, 4) Should return 4. Does return: " + findLast(nums, 5, 4));
		System.out.println("findLast(nums, 4, 4) Should return 0. Does return: " + findLast(nums, 4, 4));
		System.out.println();

		System.out.println("findPrev(nums, 5, 4, 5) Should return 4. Does return: " + findPrev(nums, 5, 4, 5));
		System.out.println("findPrev(nums, 5, 5, 4) Should return 1. Does return: " + findPrev(nums, 5, 5, 4));
	}
}
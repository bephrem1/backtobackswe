/*
  Partition to K Equal Sum Subsets - LeetCode: https://leetcode.com/problems/partition-to-k-equal-sum-subsets

  An adaption of the answer from user "climberig" on Leetcode.
  Link: https://leetcode.com/problems/partition-to-k-equal-sum-subsets/discuss/108730/JavaC%2B%2BStraightforward-dfs-solution

  Revision by Benyam Ephrem (Jan. 9th 2019)

  This code passes all Leetcode test cases as of Jan. 9th 2019
  This Code's Runtime:           77 ms*, faster than 17.28% of Java online submissions for Partition to K Equal Sum Subsets.
  Leetcode's Solution Runtime:    98 ms, faster than 11.70% of Java online submissions for Partition to K Equal Sum Subsets.

  * Note: This is FAST for the backtracking approach to this question. The DP solution to this question
  is asymptotically faster. This is still an accepted solution.

  The video to explain this code is here: [a link will live here someday]
*/

 public boolean canPartitionKSubsets(int[] arr, int k) {

  /*
    Get the sum of all items in the array. We will use this to
    divide by k to get the sum that each bucket needs to hit
  */
  int sumOfAllArrayItems = IntStream.of(arr).sum();

  /*
    1.) k can not be 0 because we can not fill 0 buckets

    2.) sumOfAllArrayItems % k must be 0. If it is not then
    we would have to have to fill buckets to a floating point
    sum which would be impossible with only integers.
  */
  if (k == 0 || sumOfAllArrayItems % k != 0) {
    return false;
  }

  return canPartition(0, arr, new boolean[arr.length], k, 0, sumOfAllArrayItems / k);
}

boolean canPartition(int iterationStart, int[] arr, boolean[] used, int k,
                      int inProgressBucketSum, int targetBucketSum) {

  /*
    If we have filled all k - 1 buckets up to this point and we are now on
    our last bucket, we can stop and be finished.
    
    Example:

    arr = [4, 3, 2, 3, 5, 2, 1]
    k = 4

    targetBucketSum = 5

    If we get to the point in our recursion that k = 1 that means we have filled
    k - 1 buckets (4 - 1 = 3). 3 buckets have been filled, each a value of 5 meaning
    we have "eaten" 15 "points" of value from an array that sums to 20.

    This means we have 5 "points" to extract from the array and that for sure will fill
    the last bucket. So at the point there is 1 bucket left, we know we can complete the
    partitioning (we don't have to though, we just want to know whether we can or not).
  */
  if (k == 1) {
    return true;
  }

  /*
    Bucket full. continue the recursion with k - 1 as the new k value, BUT the
    targetBucketSum stays the same. We just have 1 less bucket to fill.
  */
  if (inProgressBucketSum == targetBucketSum) {
    return canPartition(0, arr, used, k - 1, 0, targetBucketSum);
  }

  /*
    Try all values from 'iterationStart' to the end of the array ONLY if they have
    not been used up to this point in the recursion's path.
  */
  for (int i = iterationStart; i < arr.length; i++) {
    if (!used[i]) {
      used[i] = true;
      /*
        See if we can partition from this point with the item added
        to the current bucket progress
      */
      if (canPartition(i + 1, arr, used, k, inProgressBucketSum + arr[i], targetBucketSum)) {
        return true;
      }
      used[i] = false;
    }
  }

  /*
    Partitioning from this point is impossible. Whether we are at the
    top level of the recursion or deeper into it.
  */
  return false;
}

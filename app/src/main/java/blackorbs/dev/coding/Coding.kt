package blackorbs.dev.coding

fun main1(){
//    println(
//        sortCards(listOf("Jack", "King", "Queen", "2", "3", "4", "5", "6", "7", "8", "9"))
//    )
//    println(maxNonRepChars("absafgsbkhjssalmkm"))
//    println(isAnagram("anagram", "nagaram"))
//    println(isPalindrome("A man, a plan, a canal: Panama"))
//    println(threeSum(intArrayOf(-1,0,1,2,-1,-4)))
    println(longestPalindrome("babad"))
}

fun longestPalindrome(s: String): String{
    if(s.isEmpty()) return s
    var start = 0
    var maxLen = 0

    fun expand(leftStart: Int, rightStart: Int){
        var left = leftStart
        var right = rightStart
        while(left >= 0 && right < s.length && s[left] == s[right]){
            val len = right - left +1
            if(len > maxLen){
                start = left
                maxLen = len
            }
            left --; right++
        }
    }

    for(i in s.indices){
        expand(i, i) // odd-length palindromes
        expand(i, i+1) // even-length palindromes
    }

    return s.substring(start, start+maxLen)
}

fun threeSum(nums: IntArray): List<List<Int>>{
    nums.sort()
    val result = mutableListOf<List<Int>>()

    for(i in nums.indices){

        if(i > 0 && nums[i] == nums[i-1]) continue // skip duplicate
        var left = i + 1
        var right = nums.size - 1
        while(left < right){
            val sum = nums[i] + nums[left] + nums[right]
            when{
                sum < 0 -> left++
                sum > 0 -> right--
                else -> {
                    result.add(listOf(nums[i], nums[left], nums[right]))
                    left++; right--
                    while(left < right && nums[left] == nums[left-1]) left++ // skip duplicates
                    while(left < right && nums[right] == nums[right+1]) right-- // skip duplicates
                }
            }
        }
    }

    return result
}

fun isPalindrome(s: String): Boolean{
    var i = 0
    var j = s.length - 1
    while(i < j){
        while(i < j && !isAlphanumeric(s[i])) i++
        while(i < j && !isAlphanumeric(s[j])) j--
        if(s[i].lowercaseChar() != s[j].lowercaseChar()){
            return false
        }
        i++; j--
    }
    return true
}
//fun isAnagram(s1: String, s2: String): Boolean{
//    val freq = mutableMapOf<Char, Int>()
//    for(c in s1){
//        freq[c] = (freq[c]?:0) +1
//    }
//    for(c in s2){
//        freq[c] = (freq[c]?:0) +1
//        if(freq[c] == 0) freq.remove(c)
//    }
//    return freq.isEmpty()
//}
fun isAnagram(s1: String, s2: String): Boolean{
    if(s1.length != s2.length) return false

    val count = IntArray(26)
    for(c in s1){
        count[c - 'a']++
    }
    for(c in s2){
        count[c - 'a']--
        if(count[c - 'a'] < 0) return false
    }
    return true
}

fun isAlphanumeric(c: Char): Boolean{
    return c.code >= '0'.code && c.code <= '9'.code ||
            c.code >= 'a'.code && c.code <= 'z'.code ||
            c.code >= 'A'.code && c.code <= 'Z'.code
}

fun maxNonRepChars(s: String): String{
    val seen = mutableSetOf<Char>()
    var start = 0
    var maxStart = 0
    var maxLen = 0

    for(end in s.indices){
        while(s[end] in seen) {
            seen.remove(s[start])
            start++
        }
        seen.add(s[end])
        if(end - start + 1 > maxLen){
            maxStart = start
            maxLen = end - start + 1
        }
    }
    return s.substring(maxStart, maxStart+maxLen)
}

fun sortCards(cards: List<String>): List<String>{
    val rankMap = mapOf(
        "Jack" to 11,
        "Queen" to 12,
        "King" to 13
    )

    fun getRank(card: String): Int{
        return rankMap[card] ?: card.toIntOrNull() ?: 0
    }

    return cards.sortedBy(::getRank)
}

fun main() {
    println(twoSum(intArrayOf(3,2,4), 6))
    println(median(intArrayOf(1), intArrayOf(2,4,6,7)))
    println(maxSubArray(intArrayOf(-2,1,-3,4,-1,2,1,-5,4)))
    println(reversed("Jamiu"))
    println(isValid("{[(]}"))
}

fun isValid(string: String): Boolean{
    val stack = ArrayDeque<Char>()
    val map = mapOf(')' to '(', ']' to '[', '}' to '{')
    string.forEach{ s ->
        if(s in "({(["){
            stack.addLast(s)
        }
        else if(stack.isEmpty() || stack.removeLast() != map[s]){
            return false
        }
    }
    return stack.isEmpty()
}

fun reversed(s: String): String{
    val sb = StringBuilder()
    for(i in s.length-1 downTo 0){
        sb.append(s[i])
    }
    return sb.toString()
}

fun maxSubArray(nums: IntArray): Pair<Int, IntRange>{
    var currentSum = nums[0]
    var maxSum = currentSum
    var start = 0
    var end = 0
    var start0 = 0
    for(i in 1 until nums.size){
        val num = nums[i]
        currentSum += num
        if(num > currentSum){
            start0 = i
            currentSum = num
        }
        if(currentSum > maxSum){
            maxSum = currentSum
            start = start0
            end = i
        }
    }
    return maxSum to start..end
}

fun median(nums1: IntArray, nums2: IntArray): Double{
    var i = 0
    var j = 0
    val total = nums1.size + nums2.size
    val mid = total/2
    while(i < nums1.size && j < nums2.size){
        val num1 = nums1[i]
        val num2 = nums2[j]
        if(i+j+1 >= mid){
            if(total%2 == 0){
                return (num1 + num2).toDouble()/2
            }
            else {
                return (if(num2 > num1) num2 else num1).toDouble()
            }
        }
        if(num2 > num1){
            i++
        }
        else{
            j++
        }
    }
    return 0.0
}

fun twoSum(nums: IntArray, target: Int): IntArray{
    val map = mutableMapOf<Int, Int>()
    nums.forEachIndexed{ i, num ->
        val num0 = target - num
        map[num0]?.let { i0 ->
            return intArrayOf(i0, i)
        }
        map[num] = i
    }
    return intArrayOf()
}
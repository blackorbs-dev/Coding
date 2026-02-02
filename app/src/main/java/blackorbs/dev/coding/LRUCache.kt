package blackorbs.dev.coding

fun main() {
    val cache = LRUCache<Int>(2)
    cache.put(1, 1)
    cache.put(2, 2)
    println(cache.get(1)) // Returns 1
    cache.put(3, 3) // Evicts key 2
    println(cache.get(2)) // Returns null (not found)
    cache.put(4, 4) // Evicts key 1
    println(cache.get(1)) // Returns null (not found)
    println(cache.get(3)) // Returns 3
    println(cache.get(4)) // Returns 4
}

//class LRUCache<T>(private val capacity: Int){
//    private class Node<T>(
//        val key: Int,
//        var value: T?,
//        var next: Node<T>? = null,
//        var prev: Node<T>? = null
//    )
//
//    private val head = Node<T>(-1, null)
//    private val tail = Node<T>(-1, null)
//
//    init {
//        head.next = tail
//        tail.prev = head
//    }
//
//    private val cache = mutableMapOf<Int, Node<T>>()
//
//    fun get(key: Int): T?{
//        val node = cache[key] ?: return null
//        moveToFront(node)
//        return node.value
//    }
//
//    fun put(key: Int, value: T){
//        if(cache.containsKey(key)){
//            val node = cache[key]!!
//            node.value = value
//            moveToFront(node)
//        }
//        else{
//            val node = Node(key, value)
//            addToFront(node)
//            cache[key] = node
//            if(cache.size > capacity){
//                val lru = removeLast()
//                cache.remove(lru.key)
//            }
//        }
//    }
//
//    private fun moveToFront(node: Node<T>){
//        removeNode(node)
//        addToFront(node)
//    }
//
//    private fun addToFront(node: Node<T>){
//        node.next = head.next
//        node.prev = head
//        head.next?.prev = node
//        head.next = node
//    }
//
//    private fun removeNode(node: Node<T>){
//        node.prev?.next = node.next
//        node.next?.prev = node.prev
//    }
//
//    private fun removeLast(): Node<T>{
//        val lru = tail.prev!!
//        removeNode(lru)
//        return  lru
//    }
//
//    override fun toString(): String {
//        val sb = StringBuilder()
//        var current = head.next
//        while (current != tail){
//            sb.append(current!!.key)
//                .append(':')
//                .append(current.value)
//            if(current.next != tail){
//                sb.append(" -> ")
//            }
//            current = current.next
//        }
//        return sb.toString()
//    }
//}
import java.io.File

class ListRandom {
    lateinit var head: ListNode
    lateinit var tail: ListNode
    var count: Int = 1

    fun serialize(file: File) {
        val writer = file.bufferedWriter()
        val randMap: MutableMap<ListNode, Pair<Int?, Int?>> = mutableMapOf()
        var current: ListNode? = head
        //Going from head to tail and collecting forward-randoms and self-randoms
        while (current != null) {
            if (current.rand != null && !randMap.containsKey(current.rand)) {
                randMap[current.rand!!] = Pair(count, null)
            }
            if (randMap.containsKey(current)) {
                randMap[current] = Pair(randMap[current]?.first, count)
            }
            writer.write("$count ${current.data}")
            writer.newLine()
            current = current.next
            count++
        }
        //Going backwards to assign backward-randoms
        current = tail
        while (current != null) {
            count--
            if (current.rand != null && !randMap.containsKey(current.rand)) {
                randMap[current.rand!!] = Pair(count, null)
            }
            if (randMap.containsKey(current)) {
                randMap[current] = Pair(randMap[current]?.first, count)
            }
            current = current.prev
        }
        writer.write("nodes_end\n")
        for (entry in randMap) {
            writer.write("${entry.value.first} ${entry.value.second}\n")
        }
        println("Serialization complete\n")
        writer.close()
    }

    fun deserialize(file: File) {

    }
}

class ListNode {
    var prev: ListNode? = null
    var next: ListNode? = null
    var rand: ListNode? = null // произвольный элемент внутри списка
    var data: String? = null

}
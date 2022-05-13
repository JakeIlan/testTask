import java.io.File

class ListRandom {
    lateinit var head: ListNode
    lateinit var tail: ListNode
    var count: Int = 1

    fun serialize(file: File) {
        count = 1
        val writer = file.bufferedWriter()
        val randMap: MutableMap<ListNode, Pair<MutableList<Int>, Int?>> = mutableMapOf()
        var current: ListNode? = head
        var nodes = ""

        //Going from head to tail and collecting forward-randoms and self-randoms
        while (current != null) {
            if (current.rand != null) {
                if (randMap[current.rand!!] == null) {
                    randMap[current.rand!!] = Pair(mutableListOf(count), null)
                } else {
                    randMap[current.rand!!]!!.first.add(count)
                }
            }
            if (randMap.containsKey(current)) {
                randMap[current] = randMap[current]?.let { Pair(it.first, count) }!!
            }
            nodes += "${current.data}\n"
            current = current.next
            count++
        }
        writer.write("${count-1}\n")
        writer.write(nodes)

        //Going backwards to assign backward-randoms
        current = tail
        while (current != null) {
            count--
            if (current.rand != null && !randMap.containsKey(current.rand)) {
                if (randMap[current.rand!!] == null) {
                    randMap[current.rand!!] = Pair(mutableListOf(count), null)
                } else {
                    randMap[current.rand!!]!!.first.add(count)
                }
            }
            if (randMap.containsKey(current)) {
                randMap[current] = randMap[current]?.let { Pair(it.first, count) }!!
            }
            current = current.prev
        }

        //Saving random connections
        for (entry in randMap) {
            if (entry.value.first.size == 1) writer.write("${entry.value.first[0]} ${entry.value.second}\n")
            else {
                for (num in entry.value.first) {
                    writer.write("$num ${entry.value.second}\n")
                }
            }
        }
        writer.write("end")
        writer.close()
        println("Serialization complete\n")
    }

    fun deserialize(file: File) {
        val reader = file.bufferedReader()
        val writer = File("test.txt").bufferedWriter()

        //Reading amount of elements
        var line = reader.readLine()
        val amount = line.toInt() - 1

        //Reading and assigning elements
        line = reader.readLine()
        var current: ListNode? = ListNode()
        current?.data = line
        head = current!!
        line = reader.readLine()
        for (i in 1..amount) {
            val next = ListNode()
            next.data = line
            current!!.next = next
            next.prev = current
            current = next
            line = reader.readLine()
        }
        tail = current!!

        //Reading random connections
        val randoms: MutableMap<Int, Int> = mutableMapOf()
        while (line != "end") {
            val tokens = line.split(" ")
            randoms[tokens[0].toInt()] = tokens[1].toInt()
            line = reader.readLine()
        }

        //Restoring random connections
        val randNodes: MutableMap<ListNode, Int> = mutableMapOf()
        count = 1
        current = head
        while (current != null) {
            if (randoms.containsKey(count)) {
                randNodes[current] = randoms[count]!!
            }
            val subMap = randNodes.filterValues { it == count }
            for (entry in subMap) {
                entry.key.rand = current
            }

            current = current.next
            count++
        }
        current = tail
        while (current != null) {
            count--
            if (randoms.containsKey(count)) {
                randNodes[current] = randoms[count]!!
            }
            val subMap = randNodes.filterValues { it == count }
            for (entry in subMap) {
                entry.key.rand = current
            }
            current = current.prev
        }

        reader.close()
        writer.close()
        println("Deserialization complete\n")
    }
}

class ListNode {
    var prev: ListNode? = null
    var next: ListNode? = null
    var rand: ListNode? = null // произвольный элемент внутри списка
    var data: String? = null

}
import java.io.File


fun main() {
    val file = File("out.txt")
    //Nodes creation
    val node1 = ListNode()
    node1.data = "one"
    val node2 = ListNode()
    node2.data = "two"
    val node3 = ListNode()
    node3.data = "three"
    val node4 = ListNode()
    node4.data = "four"
    val node5 = ListNode()
    node5.data = "five"
    //Nodes connection
    node1.next = node2
    node2.prev = node1
    node2.next = node3
    node3.prev = node2
    node3.next = node4
    node4.prev = node3
    node4.next = node5
    node5.prev = node4
    //Random connections
    node2.rand = node5
    node3.rand = node1
    node4.rand = node4
    //List creation
    val nodeList = ListRandom()
    nodeList.head = node1
    nodeList.tail = node5
    //Serialization
    nodeList.serialize(file)
}
package tutorial.mr.task4

import argonaut._, Argonaut._

object Model2 {
  case class Node(name: String, age: Int, id: Int, links: List[List[Int]]) {
   // def serial() = new NodeS(name, age, id, links)
  }
  object Node {
    implicit def NodeCodecJson: CodecJson[Node] =
      casecodec4(Node.apply, Node.unapply)("name", "age", "id", "links")
  }
}
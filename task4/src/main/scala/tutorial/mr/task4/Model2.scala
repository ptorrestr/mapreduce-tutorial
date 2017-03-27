package tutorial.mr.task4

import argonaut._, Argonaut._

object Model2 {
  // We define an object with the require attributes
  case class Node(name: String, age: Int, id: Int, links: List[List[Int]]) {
    // Here we can do any preprocessing to the objects
  }
  // We call argonout functions "apply" and "unapply". 
  // This module will read the fields given: name, age, etc.
  // and will use them as the parameters for Node class.
  object Node {
    implicit def NodeCodecJson: CodecJson[Node] =
      casecodec4(Node.apply, Node.unapply)("name", "age", "id", "links")
  }
}
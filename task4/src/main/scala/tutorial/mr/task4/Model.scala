package tutorial.mr.task4

import scalaz._, Scalaz._
import argonaut._, Argonaut._

object Model {
  case class Author(name: String, birthdate: String, bio: String) {
    def keywords():Array[String] = bio.split("\\s")
  }
  
  object Author {
    implicit def AuthorCodecJson: CodecJson[Author] =
      casecodec3(Author.apply, Author.unapply)("name", "birthdate", "bio")
  }
}
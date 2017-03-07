package tutorial.mr.task4

import org.apache.spark.SparkContext

trait Managed[T] {
  def onEnter(): T
  def onExit(t:Throwable = null): Unit
  def attempt(block: => Unit): Unit = {
    try { block } finally {}
  }
}

class ManagedSC(out:SparkContext) extends Managed[SparkContext] {
  def onEnter(): SparkContext = out
  def onExit(t:Throwable = null): Unit = {
    attempt(out.stop())
    if (t != null) throw t
  }
}

package object embeddedspark {
  def using[T <: Any](managed: Managed[T])(block: T => Unit) {
    val resource = managed.onEnter()
    var exception = false
    try { block(resource) } catch  {
      case t:Throwable => exception = true; managed.onExit(t)
    } finally {
      if (!exception) managed.onExit()
    }
  }
    
  implicit def os2managed(out:SparkContext): Managed[SparkContext] = {
    return new ManagedSC(out)
  }
}


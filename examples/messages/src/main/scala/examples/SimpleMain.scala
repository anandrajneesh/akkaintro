package examples

import scala.concurrent.duration._
import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout

class myActor extends Actor {
  def receive = {
    
    case "Hello" => sender ! "world"

    case x: String => println("Hello, " + x)

    case _ =>
  }
  
}

object SimpleMain {

  def main(args: Array[String]): Unit = {
        
    val system = ActorSystem("mySystem")
    
    implicit val timeout = Timeout(5 seconds)
    
    implicit val executioncontext = system.dispatcher
    
    val myActor = system.actorOf(Props[myActor], "myactor1")
    
    myActor ! "Jan"
    
    val future = myActor ? "Hello"
    
    future.map(result => println(result))
    
    system.shutdown
    
    system.awaitTermination
    
  }

}
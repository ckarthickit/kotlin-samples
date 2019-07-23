import kotlin.native.concurrent.Worker

class SomeDataForWorker(val stringParam: String = "Worker") {

}
class WorkerResult(val result:String)

fun main() {
    val worker: Worker<String,WorkerResult> = Worker.start()
    worker.execute(TransferMode.SAFE, { SomeDataForWorker() }) {
   // data returned by the second function argument comes to the
   // worker routine as 'input' parameter.
   input : WorkerResult->
   // Here we create an instance to be returned when someone consumes result future.
   WorkerResult(input.result + " result")
}

}
/*
@Author: Krishnan Raman.
Based on Lee Jacobson's article at http://www.theprojectspot.com/tutorial-post/simulated-annealing-algorithm-for-beginners/6
*/
object SimulatedAnnealing {

  val (initialTemperature, coolingRate) = (100000.0, 0.0003)

  case class City(x:Int,y:Int)

  def distance(a:City, b:City) = math.sqrt(List((a.x-b.x),(a.y-b.y)).map(x=>x*x).sum)

  def mkTour(tour:Seq[City]) = {
    val (i,j) = ( util.Random.nextInt(tour.size), util.Random.nextInt(tour.size) )
    tour
    .patch(i,Seq(tour(j)),1)
    .patch(j,Seq(tour(i)),1)
  }

  def length(tour:Seq[City]) = tour.foldLeft(0.0,tour.head)((a,b)=>(a._1 + distance(a._2,b),b))._1

 // If the new solution is better, accept it, else compute acceptance probability
  def acceptanceProbability(energies:(Double,Double), temperature:Double) = {
    val diff = energies._1 - energies._2
    if (diff < 0 ) 1.0 else math.exp(diff/temperature)
  }

  def compute( best:Seq[City], temp:Double):Seq[City] = {
    if (temp > 1) {
          val newSolution = mkTour( best )
          val currentEnergy = length(best)
          val neighbourEnergy = length(newSolution)

          // Decide if we should accept the neighbour
          val accept = (acceptanceProbability((currentEnergy, neighbourEnergy), temp) > math.random) &&
                       (length(newSolution) < length(best))
          compute( if (accept) {
                    printf("\nLength: %.2f, Temperature: %.2f", length(newSolution), temp)
                    newSolution
                  } else best, (1-coolingRate)*temp)
    } else best
  }

  def main(args:Array[String]) = {
    val tour = Seq(City(60, 200),
        City(180, 200),
        City(80, 180),
        City(140, 180),
        City(20, 160),
        City(100, 160),
        City(200, 160),
        City(140, 140),
        City(40, 120),
        City(100, 120),
        City(180, 100),
        City(60, 80),
        City(120, 80),
        City(180, 60),
        City(20, 40),
        City(100, 40),
        City(200, 40),
        City(20, 20),
        City(60, 20),
        City(160, 20))

    val best = compute( tour, initialTemperature)
    printf("\nFinal solution distance: %.2f\n",length(best))
    println("Tour: " + best)
  }
}

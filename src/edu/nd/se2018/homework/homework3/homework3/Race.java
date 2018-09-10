package homework3;
//Anna Smith, Homework 3

public class Race {
	//array of 5 horses to run race
	Horse[] raceHorses = new Horse[5];

	public void enrollHorse(String name, int horseNumber, double speed, Horse horse) {
	//add horse to race
		horse.setName(name); //set name of horse object created in 
		horse.setSpeed(speed); //set max speed
		
		//check that horseNumber is between 0 and 4 and unassigned in array
		if (horseNumber>=0 && horseNumber<=4 && raceHorses[horseNumber]==null) {
			raceHorses[horseNumber]	= horse;	
		} else {
			System.out.println("Error in entering horse number "+horseNumber);
			System.exit(1);
		}
	}
	
	public void resetStrategy(int horseNumber, Horse horseUpdated) {
	//reset run strategy of horse after creation
		Horse horseOrigStrategy = raceHorses[horseNumber];
		horseUpdated.setName(horseOrigStrategy.getName());
		horseUpdated.setSpeed(horseOrigStrategy.getSpeed());
		horseUpdated.setDistance(horseOrigStrategy.getDistance());
		raceHorses[horseNumber] = horseUpdated;
	}
	
	public String runRace() {
	//simulate race of 5 horses for 10 miles 
	//each horse moves a certain distance each minute depending on their run strategy
		double minute = 0; //minutes (time measurement)
		double maxDistance = 0; //miles (distance measurement)
		String leader = null; //name of winning horse
		
		while (maxDistance < 10) {
		//loop until at least one horse runs 10 miles
			for (Horse horse: raceHorses) {
				//increment each horse distance
				horse.move(minute); 
				//keep track of maximum distance traveled by a horse and the name of leading horse
				if (horse.getDistance() > maxDistance) {
					maxDistance = horse.getDistance();
					leader = horse.getName();
				}
			}
			if (minute%5==0) printDistances(minute);
			minute++;
		}
		if ((minute-1)%5!=0) { printDistances(minute-1); }
		announceWinner(leader);
		return leader;
	}
	
	public void printDistances(double min) {
	//print distance of each race horse after certain amount of time
		System.out.println("After "+min+" minutes...");
		for (Horse horse: raceHorses) {
			System.out.println(horse.getName()+" has run "+horse.getDistance()+" miles.");
		}
	}
	
	public void announceWinner(String name) {
	//print name of winning horse
		System.out.println("The winner is "+name+"!\n");
	}
}
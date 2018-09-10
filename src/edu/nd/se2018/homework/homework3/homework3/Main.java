package homework3;
//Anna Smith, Homework 03

public class Main {

	public static void main(String[] args) {
	//three sample races from Sakai TestExamples
		
			Race race1 = new Race();
			race1.enrollHorse("Sam",0,22,new EarlySprintStrategy());
			race1.enrollHorse("Molly",1,24,new SlowStartStrategy());
			race1.enrollHorse("Joe",2,25,new EarlySprintStrategy());
			race1.enrollHorse("Blizzard",3,25,new SlowStartStrategy());
			race1.enrollHorse("Flicker",4,25,new SteadyRunStrategy());
			race1.runRace();

			Race race2 = new Race();
			race2.enrollHorse("Sam",0,22,new EarlySprintStrategy());
			race2.enrollHorse("Molly",1,22,new EarlySprintStrategy());
			race2.enrollHorse("Joe",2,1,new SteadyRunStrategy());
			race2.enrollHorse("Blizzard",3,1,new SteadyRunStrategy());
			race2.enrollHorse("Flicker",4,1,new SteadyRunStrategy());
			race2.runRace();
			
			Race race3 = new Race();
			race3.enrollHorse("Sam",0,100,new EarlySprintStrategy());
			race3.enrollHorse("Molly",1,133.33,new SlowStartStrategy());
			race3.enrollHorse("Joe",2,1,new SteadyRunStrategy());
			race3.enrollHorse("Blizzard",3,1,new SteadyRunStrategy());
			race3.enrollHorse("Flicker",4,1,new SteadyRunStrategy());
			race3.runRace();
	}

}

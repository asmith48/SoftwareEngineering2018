package homework3;
//Anna Smith, Homework03

import org.junit.Test;

//Show that Slow Start Strategy is best for races with horse speeds less than 50 mph. 
//At speed 50 all horses run the race at the same overall pace. 
//With speeds greater than 50 mph Early Sprint Strategy is better. 
public class RaceTest3 {

	@Test
	public void test() {
		Race race = new Race();
		race.enrollHorse("testHorse",0,25,new SlowStartStrategy());
		race.enrollHorse("testHorse1",1,25,new EarlySprintStrategy());
		race.enrollHorse("testHorse2",2,25,new EarlySprintStrategy());
		race.enrollHorse("testHorse3",3,25,new SteadyRunStrategy());
		race.enrollHorse("testHorse4",4,25,new SteadyRunStrategy());
		
		assert(race.runRace().equals("testHorse"));

		Race race5 = new Race();
		race5.enrollHorse("testHorse",0,49,new SlowStartStrategy());
		race5.enrollHorse("testHorse1",1,49,new EarlySprintStrategy());
		race5.enrollHorse("testHorse2",2,49,new EarlySprintStrategy());
		race5.enrollHorse("testHorse3",3,49,new SteadyRunStrategy());
		race5.enrollHorse("testHorse4",4,49,new SteadyRunStrategy());
		
		assert(race5.runRace().equals("testHorse"));
		
		Race race2 = new Race();
		race2.enrollHorse("testHorse",0,50,new SlowStartStrategy());
		race2.enrollHorse("testHorse1",1,50,new EarlySprintStrategy());
		race2.enrollHorse("testHorse2",2,50,new EarlySprintStrategy());
		race2.enrollHorse("testHorse3",3,50,new SteadyRunStrategy());
		race2.enrollHorse("testHorse4",4,50,new SteadyRunStrategy());
		
		assert(race2.runRace().equals("testHorse"));
		
		Race race3 = new Race();
		race3.enrollHorse("testHorse",0,75,new SlowStartStrategy());
		race3.enrollHorse("testHorse1",1,75,new EarlySprintStrategy());
		race3.enrollHorse("testHorse2",2,75,new EarlySprintStrategy());
		race3.enrollHorse("testHorse3",3,75,new SteadyRunStrategy());
		race3.enrollHorse("testHorse4",4,75,new SteadyRunStrategy());
		
		assert(race3.runRace().equals("testHorse1"));
		
		Race race4 = new Race();
		race4.enrollHorse("testHorse",0,100,new SlowStartStrategy());
		race4.enrollHorse("testHorse1",1,100,new EarlySprintStrategy());
		race4.enrollHorse("testHorse2",2,100,new EarlySprintStrategy());
		race4.enrollHorse("testHorse3",3,100,new SteadyRunStrategy());
		race4.enrollHorse("testHorse4",4,100,new SteadyRunStrategy());
		
		assert(race4.runRace().equals("testHorse1"));
	}
}

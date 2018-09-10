package homework3;
//Anna Smith, Homework 03

import org.junit.Test;

//test that changing strategy of horse will affect race results
public class RaceTest1 {
	@Test
	public void test() {
		Race race = new Race();
		race.enrollHorse("testHorse",0,100,new SteadyRunStrategy());
		race.enrollHorse("testHorse1",1,100,new SteadyRunStrategy());
		race.enrollHorse("testHorse2",2,50,new SteadyRunStrategy());
		race.enrollHorse("testHorse3",3,50,new SteadyRunStrategy());
		race.enrollHorse("testHorse4",4,50,new SteadyRunStrategy());
		
		race.resetStrategy(1,new EarlySprintStrategy());

		
		Race race2 = new Race();
		race2.enrollHorse("testHorse",0,100,new SteadyRunStrategy());
		race2.enrollHorse("testHorse1",1,100,new SteadyRunStrategy());
		race2.enrollHorse("testHorse2",2,50,new SteadyRunStrategy());
		race2.enrollHorse("testHorse3",3,50,new SteadyRunStrategy());
		race2.enrollHorse("testHorse4",4,50,new SteadyRunStrategy());
		
		assert(race.runRace().equals("testHorse1"));
		assert(race2.runRace().equals("testHorse"));
	}
}


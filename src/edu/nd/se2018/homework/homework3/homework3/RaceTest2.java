package homework3;
//Anna Smith, Homework03

import org.junit.Test;

//test the Horse object get and set methods to change name and speed variables and retrieve information
public class RaceTest2 {

	@Test
	public void test() {
		Race race = new Race();
		race.enrollHorse("testHorse",0,100,new SteadyRunStrategy());
		race.enrollHorse("testHorse1",1,100,new SteadyRunStrategy());
		race.enrollHorse("testHorse2",2,50,new SteadyRunStrategy());
		race.enrollHorse("testHorse3",3,50,new SteadyRunStrategy());
		race.enrollHorse("testHorse4",4,50,new SteadyRunStrategy());
		
		race.raceHorses[0].setName("newestTestHorse");
		race.raceHorses[0].setSpeed(200);
		
		assert(race.raceHorses[1].getName().equals("testHorse1"));
		assert(race.runRace().equals("newestTestHorse"));
	}
}

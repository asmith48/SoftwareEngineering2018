package homework3;
//Anna Smith, Homework 3

abstract class Horse {
//abstract base class Horse holds name, speed, and distance variables
	String name;
	double maxSpeed; //mph
	double distance = 0; //miles
	
	Horse() {} //Constructor
	
	//abstract method move
	abstract void move(double time);
	
	//get/set methods
	void setName(String n) { this.name = n; }
	void setSpeed(double s) { this.maxSpeed = s; }
	void setDistance(double d) { this.distance = d; }
	
	String getName() { return name; }
	double getSpeed() { return maxSpeed; }
	double getDistance() { return distance; }
}

class EarlySprintStrategy extends Horse {
//Early Sprint Strategy 
	EarlySprintStrategy() { super(); } //Constructor
	
	//define movement for Early Sprint Strategy
	//first 2 miles at max speed, the rest at 75% max speed
	void move(double time) {
		if (distance <= 2) {
			distance = distance + maxSpeed*(time/60);
		} else {
			distance = distance + maxSpeed*0.75*(time/60);
		}
	}
}

class SteadyRunStrategy extends Horse {
//Steady Run Strategy
	SteadyRunStrategy() { super(); } //Constructor
	
	//define movement for Steady Run Strategy
	//entire race at 80% max speed
	void move(double time) {
		distance = distance + maxSpeed*0.8*(time/60);
	}
}

class SlowStartStrategy extends Horse {
//Slow Start Strategy
	SlowStartStrategy() { super(); } //Constructor
	
	//define movement for Slow Start Strategy
	//first 6 miles at 75% max speed, next 3 miles at 90% max speed, last mile at 100% max speed
	void move(double time) {
		if (distance <= 6) {
			distance = distance + maxSpeed*0.75*(time/60);
		} else if (distance <=9) {
			distance = distance + maxSpeed*0.9*(time/60);
		} else {
			distance = distance + maxSpeed*(time/60);
		}
	}
}





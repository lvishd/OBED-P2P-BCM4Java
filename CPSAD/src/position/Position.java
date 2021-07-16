package position;

import java.io.Serializable;

import interfaces.PositionI;

public class Position implements PositionI, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		private double x;
		private double y;
		
		
		public Position(double x, double y) 
		{
			this.x = x;
			this.y = y;
		}
	
		public double getX() 
		{
			return x;
		}

		
		public double getY() 
		{
			return y;
		}

		@Override
		public boolean equals(Object obj) 
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			if (this.x != other.x)
				return false;
			if (this.y != other.y)
				return false;
			return true;
		}


		@Override
		public double distance(PositionI other) 
		{
			if(!(other instanceof Position))
				return -1;
			Position p = (Position)other;
			return Math.sqrt(Math.pow(p.x-this.x, 2)+Math.pow(p.y - this.y, 2));
		}

		@Override
		public double distance(Object object) {
			// TODO Auto-generated method stub
			return 0;
		}

}

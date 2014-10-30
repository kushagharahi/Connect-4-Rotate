import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MainApplet extends Applet implements MouseListener, MouseMotionListener {
	
	
	final int appletWidth = 701;
	final int appletHeight = 601;
	final int rows = 7;
	final int columns = 6;
	final int gridX = appletWidth/rows;
	final int gridY = appletHeight/columns;
	final int spaceTop = appletHeight/(columns*2);
	int[][] spaces = new int[rows][columns];
	private Image dbImage;	
	private Graphics dbg;
	Color green = new Color(51, 255, 255, 128);
	Color red = new Color(255, 0,0, 128);
	Color black = new Color(0, 0, 0,  255);
	Color yellow = new Color(255, 255, 0, 255);
	int turn = 0;
	boolean win;
	int player = 0;
	//boolean
	
	public void init()
	{
		setSize(appletWidth, appletHeight+spaceTop);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		if(!win)
			if(turn % 2 == 0)
				print(g, "(Player 1) Blacks Turn");
			else
				print(g, "(Player 2) Yellows Turn");
		else
		{
			print(g, "Player " + player + " wins");
			System.out.println("Player " + player + " wins");
		}
		drawGrid(g);
		drawPieces(g);
		repaint();
	}
	
	public void drawGrid(Graphics g)
	{
		for(int x = 0; x <= rows; x++)
		{
			g.drawLine(x*gridX, spaceTop, x*gridX, appletHeight+spaceTop);
		}
		//drawLine x1, y1, x2, y2
		for(int y = 0; y <= columns; y++)
		{
			g.drawLine(0, y*gridY+spaceTop, appletWidth, y*gridY+spaceTop);
		}
	}
	
	public void drawPieces(Graphics g)
	{
		for(int x = 0; x < rows; x++)
			for(int y = 0; y < columns; y++)
			{
					if(spaces[x][y] == 1) //player 1
					{
						g.setColor(black);
						g.fillOval(x*gridX+gridX/32, y*gridY+spaceTop+gridX/32, gridX-gridX/16, gridY-gridY/16);
					}
					else if(spaces[x][y] == 2)//player 2
					{
						g.setColor(yellow);
						g.fillOval(x*gridX+gridX/32, y*gridY+spaceTop+gridX/32, gridX-gridX/16, gridY-gridY/16);
					}
			}
	}
	
	public void animatePieces(Graphics g)
	{
		
	}
	
	public void drawVaildMoveRect(Graphics g, int row, int column)
	{
		
	}
	
	public void getWin(int row, int column) //todo sort tests by most likely to least likely
	{	
		player = spaces[row][column];
		
		//vertical test todo never let these assholes do a board less than 4 x 4 #austin 
		if(column < (columns - 3))
		{
			for(int x = 1; x <= 3; x++)
			{
				if(spaces[row][column] != spaces[row][column+x])
				{
					break;
				}
				if(x == 3)
				{
					win = true;
				}
			}
		}

		//horizontal test
		if(!win)
		{
			int count = 0;
			for(int x = 0; x < rows; x++)
			{
				if(player == spaces[x][column])
					count++;
				else
					count = 0;
				if(count == 4)
				{
					win = true;
					break;
				}
			}
		}
		
		//right diagonal test
		if(!win)
		{
			int count = 0;
			int rStarting = row;
			int cStarting = column;
			
			while((rStarting != (rows - 1)) && (cStarting != 0))
			{
				rStarting = rStarting + 1;
				cStarting = cStarting - 1;
			}
		
			System.out.println("Right diag test: rStarting: " + rStarting + " cStarting: " + cStarting);
			while((cStarting < columns) && (rStarting < rows))
			{
				System.out.println("Right diag test: rStarting: " + rStarting + " cStarting: " + cStarting);
				if(spaces[rStarting][cStarting] == player)
					count++;
				else
					count = 0;
				
				
				if(count == 4)
				{
					win = true;
					break;
				}
				rStarting = rStarting - 1;
				cStarting = cStarting + 1;
			}
		}
		
		//left diagonal test
		if(!win)
		{
			int count = 0;
			int rStarting = row;
			int cStarting = column;
			
			while((rStarting != 0) && (cStarting != 0))
			{
				rStarting = rStarting - 1;
				cStarting = cStarting - 1;
			}
			
			System.out.println("Left diag test: rStarting: " + rStarting + " cStarting: " + cStarting);
		
			while((cStarting < columns) && (rStarting < rows))
			{
				System.out.println("Left diag test: rStarting: " + rStarting + " cStarting: " + cStarting);

				if(spaces[rStarting][cStarting] == player)
					count++;
				else
					count = 0;
				
				
				if(count == 4)
				{
					win = true;
					break;
				}
				rStarting = rStarting + 1;
				cStarting = cStarting + 1;
			}
		}
					
	}
	
	public void addCircle(int row, int column, int player)
	{
		spaces[row][column] = player;
	}
	
	public int nextOpenSpace(int row)
	{
		for(int y = columns-1; y >= 0; y--)
		{
			if(spaces[row][y] == 0)
				return y;
		}
		return -1;
	}
	
	public void update (Graphics g)
	{
		if (dbImage == null)
		{
			dbImage = createImage (this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics();
			}

			dbg.setColor(getBackground());
			dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

			dbg.setColor(getForeground());
			paint(dbg);

			g.drawImage(dbImage, 0, 0, this);
		}
	
	public void print(Graphics g, String message)
	{
		FontMetrics fm = g.getFontMetrics();
		g.setFont(new Font("Segoe UI Emoji", Font.PLAIN, spaceTop/2));
		int stringWidth = fm.stringWidth(message);
		g.drawString(message, appletWidth - stringWidth-stringWidth/6, spaceTop/2);
	}

	
	public void mousePressed(MouseEvent e)
	{
		if(!win)
			for(int x = 0; x <= rows; x++)
				if(e.getX() < x*gridX)
				{	
					System.out.println("Row " + x + " is clicked");
					int openSpace = nextOpenSpace(x-1);
					if(openSpace != -1)
					{
						if(turn % 2 == 0)
						{
							addCircle(x-1,openSpace, 1); //todo add player numbers
							getWin(x-1, openSpace);
						}
						else
						{
							addCircle(x-1, openSpace, 2);
							getWin(x-1, openSpace);
						}
						turn++;
						break;
					}
					else //todo add error message for row being full
					{
						System.out.println("Column is full");
						break;
					}
				}
				
					
		System.out.println("mouse clicked @ " + e.getX() + " " + e.getY());
    }
	
    public void mouseReleased(MouseEvent e)
    {

    }

    public void mouseEntered(MouseEvent e)
    {
    	
    }

    public void mouseExited(MouseEvent e)
    {

    }

    public void mouseClicked(MouseEvent e)
    {

    }

	public void mouseMoved(MouseEvent e)
	{
/*		for(int x = 0; x <= rows; x++)
			if(e.getX() < x*gridX)
			{	
				int openSpace = nextOpenSpace(x-1);
				if(openSpace != -1)
				{
					
					break;
				}
				else 
				{
					
					break;
				}
			}*/
    }

    public void mouseDragged(MouseEvent e)
    {

    }

}

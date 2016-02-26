import java.util.Random;
import java.lang.Thread;
public class TreIRow4
{
	static String[] player_Board;
	static final int BOARD_SIZE = 9, COMPUTER_MIND_FASTNESS = 2;
	static  int winCountX, winCountO, winCountComp,playCount; 
	public static void playGame()
	{
		
		boolean isPlaying = true, playAgain = true;
		int playerChoiceNum = 0; 
		String player_input, which_player, againString, computer_choice, name1, name2;
		Random rand;
		
		while(playAgain)
		{	
			player_Board= new String[BOARD_SIZE]; // vill alltid göra ny, så jag får alla platser till "null" igen! ;)
			fixNullBoard();
			paintBoard();
			which_player = "p1";
			name1 = System.console().readLine("Skriva namn för player1:		");
			name2 = System.console().readLine("Skriv namn för player2:		");
			computer_choice = System.console().readLine("Spelare X, skriv computer om du vill lira mot datorn:"+
													" annars skriv vadsomhelst förutom computer!:	");

			while(isPlaying)
			{
				if(which_player.equals("p1") && !isBoardFull())
				{
					player_input = System.console().readLine("Spelare X, dvs "  +name1+ " vilken ruta (1-9)?:	");
					try
					{
						playerChoiceNum = Integer.parseInt(player_input) - 1;

					}
					catch(NumberFormatException e)
					{
						System.out.println("You did not write a number! TRY AGAIN");
						continue;
					}

					if(!isSquareEmpty(playerChoiceNum))
					{
						which_player="p1"; // spelare 1 igen
						continue;
					}
				    else
				    {
				    	player_Board[playerChoiceNum] = "X";
				    	paintBoard();
				    	if(isThreeInRow("X")) // kolla om vinst!
				    	{
				    		System.out.println("Grattis spelare X, du har vunnit ;)");
				    		winCountX = winCountX + 1;
				    		isPlaying = false;
				    		continue;
				    	}
				    }

				    if(which_player.equals("computer") || computer_choice.equals("computer"))
				    {
				    	which_player = "computer";
				    }
				    else
				    {
				    	which_player = "p2"; //om inte vinst, byt till spelare 2!!
					}
				}
				else if(which_player.equals("p2") && !isBoardFull())//annars O
				{
					player_input = System.console().readLine("Spelare O, dvs" + name2 + " vilken ruta (1-9)?:	");
					try
					{
						playerChoiceNum = Integer.parseInt(player_input) - 1; // -1 ty nollindexering i java!

					}
					catch(NumberFormatException e)
					{
						System.out.println("You did not write a number! TRY AGAIN");
					}

					if(!isSquareEmpty(playerChoiceNum))
					{
						which_player="p2";
						continue;
					}
				    else
				    {
				    	player_Board[playerChoiceNum] = "O";
				    	paintBoard(); 
				    	if(isThreeInRow("O"))
				    	{
				    		System.out.println("Grattis spelare O, du har vunnit ;)");
				    		winCountO = winCountO + 1;
				    		isPlaying = false;
				    		continue;
				    	}

					which_player = "p1"; //byt till spelare 1
					}
				}
				else if(which_player.equals("computer") && !isBoardFull())
				{//computer
					rand = new Random();
					playerChoiceNum = rand.nextInt(9)+1;

					computerSLeep(COMPUTER_MIND_FASTNESS);

					if(!isSquareEmpty(playerChoiceNum))
					{
						which_player="computer";
						continue;
					}
				    else
				    {
				    	player_Board[playerChoiceNum] = "O";
				    	paintBoard();
				    	if(isThreeInRow("O")) // kolla om vinst!
				    	{
				    		System.out.println("Grattis datorn, du har vunnit ;)");
				    		winCountComp = winCountComp + 1;
				    		isPlaying = false;
				    		continue;
				    	}
				    }
				    which_player = "p1";

				}
				else//Här ska kollas vad som händer om det blir ett fullt spel, dvs lika eller ej
				{
					if(isThreeInRow("X"))
					{
						System.out.println("Grattis spelare X, du har vunnit ;)");
						winCountX = winCountX + 1;
				    	isPlaying = false;
					}
					else if(isThreeInRow("O") && computer_choice.equals("computer"))
					{
						System.out.println("Grattis spelare O, du har vunnit ;)");
						winCountComp = winCountComp +1;
						isPlaying=false;
					}
					else if(isThreeInRow("O")) 
					{
						System.out.println("Grattis spelare O, du har vunnit ;)");
						winCountO = winCountO + 1;
				    	isPlaying = false;
					}

					else
					{
						System.out.println("Grattis spelare, INGEN av er har vunnit! Det ar oavgjort ;)");
				    	isPlaying = false;
					}
				}
				
			}
			printScore();
			againString = System.console().readLine("Vill ni spela igen? Skriv i så fall JA, annars NEJ:	");
			againString = againString.toLowerCase(); 
			if(againString.equals("ja"))
			{
				isPlaying = true;
			}
			else
			{
				playAgain = false;
				System.out.println("Thanks for playing, bye ;)");
			}
		}
	}

	public static boolean isSquareEmpty(int ruta)
	{
		if(!(ruta>=0 && ruta<=8))
		{
			System.out.println("Try a number between 1-9");
			return false;
		}
		else
		{
			if(player_Board[ruta].equals(" "))
				return true;
			else
				return false;
		}
	}

	public static boolean isBoardFull()
	{
		for(int i=0; i<=player_Board.length-1;i=i+1)
		{
			if(player_Board[i].equals(" "))
				return false;
		}
		return true;
	}

	public static boolean isThreeInRow(String str)
	{
		if(player_Board[0]==str && player_Board[1]==str && player_Board[2]==str)
			return true;
		else if(player_Board[3]==str && player_Board[4]==str && player_Board[5]==str)
			return true;
		else if(player_Board[6]==str && player_Board[7]==str && player_Board[8]==str)
			return true;
		else if(player_Board[0]==str && player_Board[3]==str && player_Board[6]==str)
			return true;
		else if(player_Board[1]==str && player_Board[4]==str && player_Board[7]==str)
			return true;
		else if(player_Board[2]==str && player_Board[5]==str && player_Board[8]==str)
			return true;
		else if(player_Board[0]==str && player_Board[4]==str && player_Board[8]==str)
			return true;
		else if(player_Board[2]==str && player_Board[4]==str && player_Board[6]==str)
			return true;
		else
		{
			return false;
		}
	}

	public static void paintBoard()
	{
			String[] graph_Board =  {"   ","   ","   ","\n",
								" "+player_Board[0]+" |"," "+player_Board[1]+" | "," "+player_Board[2]+"|", "\n",
								"---+","---+","----", "\n",
								"---!","---!","---+", "\n",
		 						" "+player_Board[3]+" |"," "+player_Board[4]+" | "," "+player_Board[5]+"|", "\n",
		 						"---!","---!","---+", "\n",
		 						"---+","---+","----", "\n",
		  						" "+player_Board[6]+" |"," "+player_Board[7]+" | "," "+player_Board[8]+"|", "\n",
		  						"   ","   ","   ","\n"}; 
        for(int i=0;i<=graph_Board.length-1;i=i+1)
		{
			System.out.print(graph_Board[i]);
		}
	}	

	public static void fixNullBoard()
	{
		for(int k=0;k<=player_Board.length-1;k=k+1)
		{
			if(player_Board[k]==null)
			{
				player_Board[k] = " ";
			}
		}
	}

	public static void computerSLeep(int seconds)
	{
		try 
		{
			System.out.println("Hello Iam your computer, let me think exactly: "
								 + seconds + "seconds!");
    		Thread.sleep(1000*seconds);       
		} catch(InterruptedException ex) 
		{
   			Thread.currentThread().interrupt();
		}
	}

	public static void printScore()
	{
		System.out.println("The score for both of the players and the computer are: ");
		System.out.println("Player 1: " + winCountX +"\n" + "Player 2: " +winCountO + "\n" + "Computer: " + winCountComp);
	}

}
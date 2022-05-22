import javax.swing.*;
import java.util.*;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map.Entry;


public class WordCounter extends JFrame implements ActionListener{
	//Fields
	private static JPanel myPanel;
	private static JButton chooseFile;			//Button for chhosing the file
	private static JButton wordCount;			//Button for running the word count
	private static JTextArea result;			//Text Area to display the results of the word count
	private static JScrollPane scroll;
	private static String filePath;
	public static String[] pList = {"!", ";","\"", "<", "#", ">", "$", "=", "%", "?", "&", "@", "\\", "[", "\'", "]", "(","^", ")", "_", "*", "`", "+", "{", "-","}", ".", "|", "/", "~", ":", "," };
	public static String [] sList = {"i", "himself", "whom", "having", "of", "from", "where", "only", "me", "she", "this", "do", "at", "up", "why", "own", "my", "her", 
			"that", "does", "by", "down", "how", "same", "myself", "hers", "these", "did", "for", "in", "all", "so", "we", "herself", "those", "doing","doing", "with", "out",
			"any", "than", "our", "it", "am", "a", "about", "on", "both", "too", "ours", "its", "an", "is", "an", "against", "off", "each", "very", "ourselves", "itself", "are",
			"the", "between", "over", "few", "can", "you", "they", "was", "and", "into", "under", "more", "will", "your", "them", "were", "but", "through", "again", "most", "just",
			"yours", "their", "be", "if", "during", "further", "other", "dont", "yourself", "theirs", "been", "or", "before", "then", "some", "should", "yourselves", "themselves",
			"being", "because", "after", "once", "such", "now", "he", "what", "have", "as", "above", "here", "no", "him", "which", "has", "until", "below", "there", "nor", "his", "who",
			"had", "while", "to", "when", "not"};
	
	public WordCounter()
	{
		myPanel = new JPanel();
		chooseFile = new JButton("Choose File");
		wordCount = new JButton("Start word count");
		result = new JTextArea();
		scroll = new JScrollPane(result);

		//Adding the panel
		add(myPanel);

		//Adding the components into the panel 
		myPanel.add(chooseFile);
		myPanel.add(wordCount);
		myPanel.add(scroll);

		//Adding action listeners to teh buttons
		chooseFile.addActionListener(this);
		wordCount.addActionListener(this);

	}




	public static void main(String[] args) {

		WordCounter myFrame = new WordCounter();

		myFrame.setTitle("Advanced Word Counter");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(600, 400);				//xSize, ySize
		myFrame.setResizable(false);			//Giving the frame a permanent size 
		myFrame.setVisible(true);			
		result.setBounds(165, 100, 250, 250);	//(xSize, ySize, width, height) setting the size for the TextAr
		scroll.setBounds(165, 100, 250, 250);
		result.setEditable(false);
		myFrame.setLayout(null);


	}
	//Setting the filePath (used in the actionPerformed method --> if(e.getSource() == chooseFile)
	public void setFilePath(String path) 
	{
		filePath = path;
	}

	//after the path of the file has been set then we need to get file path (
	public String getFilePath()
	{
		return filePath;	//returns the path of the file
	}





	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == chooseFile)
		{
			chooseFile();				//Calling the chooseFile method that allows the user to choose the file

		}

		if(e.getSource() == wordCount) 
		{
			try {
				
				displayContent();				//Calling the displayContent function to display the content of the file into the console and total word into the result (textArea)

				
				/*
				 * The Below line of code is again to read the file and remove the punctuations from it and saving the words with their frequencies
				 * in 2 arraylists called words and frequency.
				 */
				ArrayList<String> words = new ArrayList<>();			//Words will be stored here
				ArrayList<Integer> frequency = new ArrayList<>();		//Simultaneously their frequency as well 
				File sameFile = new File(getFilePath());	//Creating another FileReader method to read the file again
				
				//Using Scanner to read the file
				Scanner scan = new Scanner(sameFile);
				
				//while loop will until the program reaches the end of the file
				while(scan.hasNext()) 
				{
					//word variable is going to be the each word in file scanned and it will be turned into lower case
					String word = scan.next().toLowerCase();
					
					String element = null;
					
					//element in words ArrayList will contain all the words without punctuations so replacing all the punctuations with "" (nothing) using the regex
					element = word.replaceAll("\\p{Punct}", "");
					
					// if the element is in the words arrayList
					if(words.contains(element)) 
					{
						int index = words.indexOf(element);					//Finding the index of that element already in the list
						frequency.set(index, frequency.get(index)+1);		//Changing the element by adding 1 to that int element
					}else 
					{	
						words.add(element);									//If it is not in the words ArrayList already then it is added
						frequency.add(1);									//And simultaneously its freq which is one at the moment will be added to the frequency arraylist
					}
				}
				
				scan.close();
			
				removeStopWords(words, frequency);							//using the user defined method to remove the stop words from the words ArrayList
				
				/*
				 * Setting the words and their frequencies in a descending order.
				 */
				
				HashMap<String, Integer> map = new HashMap<>();
				
				for(int i = 0; i<words.size();i++) 
				{
					map.put(words.get(i),frequency.get(i));			//Putting the words as the key and their frequencies as the value into a HashMap map.
				}	
				
			
			//Adding the values and keys from the map to another list to sort it 
			
			ArrayList<Map.Entry<String, Integer>> sortedMap = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());		//<---- Adding the whole map into the sortedMap arrayList 
				
			//Sorting the list using the comparator
			sortedMap.sort(new Comparator<Map.Entry<String, Integer>>(){								//sorting the list using the sort buit-in method
				public int compare(Map.Entry<String, Integer> first, Map.Entry<String, Integer> second) 	//first will be the one of the entries in the arrayList and second will be the next entry in the ArrayList <Map.Entry<String, Integer>>
				{
					if(first.getValue() < second.getValue())			//The comparator returns 1 when second is greater than first so, if it is then ...
					{
						return 1;											//returning 1 will swap the 
					}
					
					return -1;											//if the o1 is greater than o2 then which means the above if condition is false then the returning -1 will swap.
				}
				
			});
			
			for(Map.Entry<String, Integer> sorted : sortedMap) 									//sorted will be the each key - value in the sortedMap		
			{
				result.append("(" + sorted.getValue() + ", " + sorted.getKey() + ")" +"\n");	
			}
			
			
			} 
			catch (IOException e1) 
			{

				e1.printStackTrace();
			}
		}

	}


	/*
	 * The below user defined method is used to remove the stop words from the ArrayList of words and their frequencies using the for-each loop
	 * 
	 */
	
	public void removeStopWords(ArrayList<String> words, ArrayList<Integer> frequency)
	{
		
		for(String s :sList) {						//s will be each element in the sList (stop words list)
			for(int i = 0; i<words.size();i++) 
			{
				//element will be the word at index i
				String element = words.get(i);
				
				//if that word is equal to the element in sList (see the fields of the class then that word will be removed and so will its freq because both are in the same index as both were added simultaneously
				if(element.equals(s)) 
				{
					words.remove(i);			//stop word removed 
					frequency.remove(i);		//stop word's freq removed (both will be in the same index because both were added simultaneously)
				}
			}
		}
	}

	
	
	/* 
	 * The below user defined method is used for displaying the contents of the file in the console and also counting the total words
	 * in that file.
	 */

	public void displayContent() throws IOException
	{
		
		File file = new File(getFilePath());			//Creating a fileReaderobject 

		Scanner scan = new Scanner(file);				//Using the Scanner to read the fileReader object/the file


		ArrayList <String> orgWords = new ArrayList<>();

		//While loop will there is nothing to be scanned by the scanner 
		while(scan.hasNext()) 
		{										//The scanner does not scan the spaces between the words.
			String line = scan.next();
			
			//printing the content of the file into the console
			System.out.print(line + " ");	// Seprating each word because the scanner does not scans the spaces

			//each word will be added to the orgWords array with 
			orgWords.add(line);
			
		}
		//Using Integer wrapper class to wrap totalWords into an object (so that it can turned into a string when appending in the TextArea) wrapper class allows more functions 
		Integer totalWords = orgWords.size();

		//Adding the the totalWords to the application
		result.append("Total words: " + totalWords.toString() + "\n");				

		scan.close();
	}



	/*
	 * The below user defined method is used to choose the file to be used for the GUI Application
	 */

	public void chooseFile() {
		
		//JFileChooser is a GUI mechanism that allows the user to open any file.
		JFileChooser fileChooser = new JFileChooser();		

		int action = fileChooser.showOpenDialog(null); 	//opens a default dialog box where we can access the file for me it is macbookpro

		setFilePath(fileChooser.getSelectedFile().getAbsolutePath());		//using the setFilePath method to set the path to the selectedfile method
		if(action == JFileChooser.APPROVE_OPTION) {							// returns value 0 if the user chooses the file else 1 							
			result.append("\n\nThe file has been selected:\n\n");

		}
	}


	

}

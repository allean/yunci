

/*
 Copyright 2010 Daniel Bernier

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import java.util.*;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PFont;
import wordcram.*;

public class WordCloud extends PApplet {
	
	WordCram wordcram;
	
	// our headless Processing applet, to which we're drawing
    private PApplet applet = null;

    // the graphics context of the applet - all drawing operations are performed on this object
    private PGraphics context = null;
	
    public void initialize(int _width, int _height)
    {
        // create a PApplet object and retrieve its graphics context
//        initWordCram(); 
        applet = this;
        context = applet.createGraphics(_width, _height, PApplet.JAVA2D);
        
        applet.g = context;
    } // initialize
	

	
	private PFont randomFont() {
		String[] fonts = PFont.list();
		String noGoodFontNames = "Dingbats|Standard Symbols L";
		String blockFontNames = "OpenSymbol|Mallige Bold|Mallige Normal|Lohit Bengali|Lohit Punjabi|Webdings";
		Set<String> noGoodFonts = new HashSet<String>(Arrays.asList((noGoodFontNames+"|"+blockFontNames).split("|")));
		String fontName;
		do {
			fontName = fonts[(int)random(fonts.length)];
		} while (fontName == null || noGoodFonts.contains(fontName));
		System.out.println(fontName);
		return createFont(fontName, 1);
		//return createFont("Molengo", 1);
	}
	

	
	private String textFilePath() {
		return "./tao-te-ching.txt";
	}
	
	private Word[] alphabet() {
		Word[] w = new Word[26];
		for (int i = 0; i < w.length; i++) {
			w[i] = new Word(new String(new char[]{(char)(i+65)}), 26-i);
		}
		return w;
	}
	
    void render(String _filename)
    {
        if (applet == null || context == null)
        {
            System.out.println("error: GraphRenderer::render(): call initialize() before rendering.");
            return;
        }
        
	// tell the context we're starting to draw
        context.beginDraw();
        context.background(255);
//        context.fill(255);
//        context.noStroke();
        // perform drawing using normal Processing methods
		wordcram = new WordCram(this)
//		.withCustomCanvas(pg)
		.fromTextFile(textFilePath())
//		.fromWords(alphabet())
//		.upperCase()
//		.excludeNumbers()
		.withFonts(randomFont())
		.withColorer(Colorers.twoHuesRandomSats(this))
//		.withColorer(Colorers.sameColor(this, 128))
//		.withColors(0)
//		.withColorer(Colorers.complement(this, random(255), 200, 220))
		.withAngler(Anglers.mostlyHoriz())
//		.angledBetween(10, 45)
//		.withPlacer(Placers.centerClump())
		.withPlacer(Placers.swirl())
//		.withCustomCanvas(new PG)
		
		.withSizer(Sizers.byWeight(5, 90))
		
		.withWordPadding(3)
		
//		.minShapeSize(0)
//		.withMaxAttemptsForPlacement(10)
		.maxNumberOfWordsToDraw(1000)
		
//		.withNudger(new PlottingWordNudger(this, new SpiralWordNudger()))
//		.withNudger(new RandomWordNudger())
		
		;
		wordcram.drawAll();
        
	// tell the context we're finished drawing
        context.endDraw();
        
	// save the contents of the rendering context to a file
        context.save(_filename);
        
    } // render
}

/*
 * Copyright (C) 2023 Rubens A. Andreoli Jr.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package rubensandreoli.imageorganizer.gui.support;

import java.awt.Component;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * References:<br>
 * https://stackoverflow.com/questions/5931261/java-use-stringbuilder-to-insert-at-the-beginning<br>
 * https://stackoverflow.com/questions/12524826/why-should-i-use-deque-over-stack<br>
 * https://stackoverflow.com/questions/196830/what-is-the-easiest-best-most-correct-way-to-iterate-through-the-characters-of-a<br>
 * https://stackoverflow.com/questions/7569335/reverse-a-string-in-java<br>
 * https://stackoverflow.com/questions/14189262/fitting-text-to-jtextfield-using<br>
 * https://stackoverflow.com/questions/30987866/java-enforce-textfield-format-ux-00000000<br>
 * https://docs.oracle.com/javase/tutorial/uiswing/components/formattedtextfield.html<br>
 * https://stackoverflow.com/questions/8075373/path-separator-vs-filesystem-getseparator-vs-system-getpropertyfile-separato<br>
 * https://stackoverflow.com/questions/58631724/paths-get-vs-path-of<br>
 * https://stackoverflow.com/questions/811248/how-can-i-use-drag-and-drop-in-swing-to-get-path-path<br>
 * http://zetcode.com/tutorials/javaswingtutorial/draganddrop/<br>
 * https://stackoverflow.com/questions/304932/jfilechooser-hangs-sometimes
 * 
 * @author Rubens A. Andreoli Jr.
 */
public class PathField extends javax.swing.JTextField{
    private static final long serialVersionUID = 1L;
    
    public static final int FILES_ONLY = 0;
    public static final int DIRECTORIES_ONLY = 1;
    public static final int FILES_AND_DIRECTORIES = 2;
    public static final int MIN_LENGTH = 5;
    
    private int mode;
    private File file;
    private int length;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PathField(int mode, int length){
        this.mode = mode;
        this.length = length;
        setEditable(false);
        setDragEnabled(true);
    }

    public PathField(int mode){
        this(mode, 0);
    }
    
    public PathField(){
        this(FILES_AND_DIRECTORIES, 0);
    }

    @Override
    public void setDragEnabled(boolean b) {
        if(b) SwingUtils.setDropTarget(this, f -> setText(f));
        else setDropTarget(null);
    }

    @Override
    public void setText(String path){
        if(path == null || path.isBlank()) {
            clear();
        }else if(!setText(new File(path))){
            throw new IllegalArgumentException("file "+path+" doesn't match set mode "+mode);
        }
    }
    
    public boolean setText(File file){
        return setText(file, false);
    }
    
    private boolean setText(File file, boolean validated){
        if(file == null){
            clear();
            return false;
        }
        
        if(!validated){
            if ((mode!=FILES_AND_DIRECTORIES) && (mode==FILES_ONLY && !file.isFile()) || (mode==DIRECTORIES_ONLY && !file.isDirectory())){
                return false;
            }
        }
        
        SwingUtils.getChooser(mode).setSelectedFile(new File(file, File.separator));
        super.setText(maskPathname(file.getPath(), length));
        this.file = file;
        fireActionPerformed();
        return true;
    }
    
    private static String normalize(String pathname){
        return new File(pathname).getPath();
    }
    
    private static final int MASKED_FILENAME_MIN_LENGTH = 5;
    private static final String separator = File.separator;
    private static final Pattern FOLDER_PATTERN = Pattern.compile("([^"+Matcher.quoteReplacement(separator)+"]*["+Matcher.quoteReplacement(separator)+"]+)");
    
    private static String maskPathname(String pathname, int maxLenght){
        if(pathname.isEmpty()) return "";
        pathname = normalize(pathname);
        if((maxLenght < MASKED_FILENAME_MIN_LENGTH) || (pathname.length() <= maxLenght)) return pathname;
        
        String formated = pathname.substring(pathname.length()-maxLenght, pathname.length());
        formated = formated.replaceFirst("([^\\"+separator
                +"]{3}(?=\\"+separator
                +"))|(.{3})(?=[^\\"+separator
                +"]*$)", "..."); //or only (.{3,}?(?=\/))|(.{3})

        String root = getRoot(pathname);
        if(root != null){
            formated = formated.replaceFirst(".{"+root.length()
                    +",}(\\.{3})|(^.{"+(root.length()+3)
                    +",}?(?=\\"+separator+"))", Matcher.quoteReplacement(root)+"..."); //if separator is "\" Matcher.quoteReplacement(root)
            int index = formated.indexOf("..."+separator);
            if(index < root.length()) formated = formated.substring(Math.max(0, index));
        }

        return formated;
    }
    
    private static String getRoot(String pathname){
        pathname = normalize(pathname);
        final Matcher matcher = FOLDER_PATTERN.matcher(pathname);
        final StringBuilder sb = new StringBuilder();
        while(matcher.find()){
            final String node = matcher.group(1);
            sb.append(node);
            if(!node.startsWith("http") && !node.startsWith(separator)) break;
        }
        if(sb.length() == 0) return null;
        return sb.toString();    
        
        // <editor-fold defaultstate="collapsed" desc=" ALTERNATIVE ">
//        final String[] tokens = pathname.split("[/\\\\]");
//        if(tokens.length == 1) {
//            if(!pathname.contains(".")) return tokens[0]+SEPARATOR;
//            return null;
//        }
//        final StringBuilder sb = new StringBuilder();
//        boolean found = false;
//        int i = 0;
//        for (String token : tokens) {
//            if(token.isEmpty()){
//                if(!found) sb.append(SEPARATOR);
//            }else{
//                if(!found){
//                    sb.append(token);
//                    if(!token.startsWith("http")) found = true;
//                    else sb.append(SEPARATOR);
//                }else{
//                    break;
//                }
//            }
//            i++;
//        }
//        if(found){ 
//            if(i <= tokens.length)sb.append(SEPARATOR);
//            return sb.toString();
//        }else{
//            return null;
//        }
        // </editor-fold>
    }
    
    public boolean select(Component parent){
        return setText(SwingUtils.selectFile(parent, mode), true);
    }
    
    public void clear(){
        super.setText("");
        file = null;
    }
    
    public void setMode(int mode){
        clear();
        this.mode = mode;
    }
    
    @Override
    public String getText() {
        return file==null? "" : file.getPath();
    }

    public void setLenght(int length) {
        if(length < MIN_LENGTH) throw new IllegalArgumentException("parameter length "+length+" < "+MIN_LENGTH);
        this.length = length;
        if(file != null) setText(file.getPath());
    }

}

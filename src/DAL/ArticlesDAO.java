package DAL;

import Logic.Article;
import Logic.OrderItem;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ArticlesDAO {
    ArrayList<Article> articles;

    public ArrayList<Article> getArticles()
    {
        articles = new ArrayList<>();
        Path path = Paths.get("src/Database/articles.csv");
        File src = new File(String.valueOf(path));
        int count = 0;
        try {
            Scanner scanner = new Scanner(src);
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] columns = line.split(",");
                //ID, First name, Last name, City, Address, Phone number, Email
                Article article = new Article(columns[0],columns[1],columns[2],columns[3],columns[4],columns[5]);
                if (count != 0) {
                    this.articles.add(article);
                }
                count++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return articles;
    }
    /*public void editStock(String toEdit, String edited)
    {
        Path path = Paths.get("src/Database/articles.csv");
        File src = new File(String.valueOf(path));

        try {
            BufferedReader reader = new BufferedReader(new FileReader(src));
            //BufferedWriter writer = new BufferedWriter(new FileWriter(src));
            //Writer writer = new FileWriter(src);
            String currentLine;
            while ((currentLine = reader.readLine()) != null)
            {
                String deleted = currentLine.trim();
                if (deleted.equals(toEdit))
                {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(src,true));
                    writer.newLine();
                    writer.write(edited);
                    writer.close();
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public void editStock(Article article)
    {
        articles = getArticles();
        for (Article entry :
                articles) {


            if (article.getModel().equals(entry.getModel()))
            {
                articles.remove(entry);
                articles.add(article);
                break;
            }
        }
        Path path = Paths.get("src/Database/articles.csv");
        File src = new File(String.valueOf(path));

        try {
            //BufferedReader reader = new BufferedReader(new FileReader(src));
            BufferedWriter writer = new BufferedWriter(new FileWriter(src));
            //Writer writer = new FileWriter(src);
            writer.write("brand,model,acoustic,type,price,stock");
            writer.newLine();
            String currentLine;
            for (Article entry :
                    articles) {


                writer.write(entry.getBrand()+","+entry.getModel()+","+entry.getAcoustic()+","+entry.getType()+","+entry.getPrice()+","+entry.getStock());
                writer.newLine();

            }

            writer.close();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package bolo.sellmethiskatana;

import necesse.engine.GlobalData;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

public class Config {

    private Integer price = 2000;
    private Integer chance = 4;

    public Config() {
        this(2000, 4);
    }

    public Config(Integer price, Integer chance) {
        this.price = price;
        this.chance = chance;
    }

    public Config(String configFileName) {
        System.out.println("Loading config for Sell Me This Katana! Mod...");
        String filename = GlobalData.rootPath() + "/settings/sellmethiskatana/" + configFileName;
        try {
            File file = new File(filename);
            if (!file.exists()) createNewFile(file);

            InputStreamReader isr = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            loadConfig(br);
            br.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadConfig(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.length() != 0 && !line.startsWith("#")) {
                String[] temp = line.split("=");
                if (Objects.equals(temp[0], "price")) this.setPrice(Integer.parseInt(temp[1]));
                if (Objects.equals(temp[0], "chance")) this.setChance(Integer.parseInt(temp[1]));
            }
        }
    }

    private void createNewFile(File file) throws IOException {
        if (!file.getParentFile().mkdirs() && !file.createNewFile()) {
            throw new IOException("Error creating file: " + file.toPath());
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8))) {
            writer.write("# lines starting with # are comments and will be ignored");
            writer.write("\n#");
            writer.write("\n# price indicates the price of the item in the travelling merchant shop");
            writer.write("\nprice=2000");
            writer.write("\n#");
            writer.write("\n# chance is an Integer and indicates the chance of the item appearing in the travelling merchant shop");
            writer.write("\n# this parameter works as if 100% chance was divided by it, 0 is 100% chance (always), 2 is 50%, 4 is 25%");
            writer.write("\nchance=4");
        }
    }

    public Integer getPrice() {
        return this.price;
    }

    private void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getChance() {
        return this.chance;
    }

    private void setChance(Integer chance) {
        this.chance = chance;
    }

}

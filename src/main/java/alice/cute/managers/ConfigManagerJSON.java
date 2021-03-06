package alice.cute.managers;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import alice.cute.module.Module;
import alice.cute.module.modules.render.VirtualBlock;
import alice.cute.setting.Checkbox;
import alice.cute.setting.ColorPicker;
import alice.cute.setting.Keybind;
import alice.cute.setting.Mode;
import alice.cute.setting.Setting;
import alice.cute.setting.Slider;
import alice.cute.setting.SubSetting;

public class ConfigManagerJSON extends ManagerBase
{
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void createDirectory() throws IOException 
    {
        if (!Files.exists(Paths.get("cute/")))
            Files.createDirectories(Paths.get("cute/"));

        if (!Files.exists(Paths.get("cute/modules")))
            Files.createDirectories(Paths.get("cute/modules"));

        if (!Files.exists(Paths.get("cute/gui")))
            Files.createDirectories(Paths.get("cute/gui"));

        if (!Files.exists(Paths.get("cute/social")))
            Files.createDirectories(Paths.get("cute/social"));
    }

    public static void registerFiles(String name, String path) throws IOException 
    {
    	String _path = "cute/" + path + "/" + name + ".json";
    	
        if (!Files.exists(Paths.get(_path))) 
        {
            Files.createFile(Paths.get(_path));
            return;
        }
        
        
        File file = new File(_path);
        file.delete();
        Files.createFile(Paths.get(_path));
    }

    public static void saveConfig() 
    {
        try 
        {
            saveModules();
            saveBlockList();
//            saveGUI();
//            saveHUD();
//            saveFriends();
//            saveEnemies();
        }
        catch (IOException ignored) 
        {

        }
    }

    public static void loadConfig() 
    {
        try 
        {
            createDirectory();
            LoadBlockList();
            loadModules();
            
//            loadGUI();
//            loadHUD();
//            loadFriends();
//            loadEnemies();
        } 
        catch (IOException ignored) 
        {

        }
    }

    public static void saveModules() throws IOException 
    {
        for (Module module : ModuleManager.getModules()) 
        {
            registerFiles(module.getName(), "modules");
            OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("cute/modules/" + module.getName() + ".json"), StandardCharsets.UTF_8);

            JsonObject moduleObject = new JsonObject();
            JsonObject settingObject = new JsonObject();
            JsonObject subSettingObject = new JsonObject();

            moduleObject.add("Name", new JsonPrimitive(module.getName()));
            moduleObject.add("Enabled", new JsonPrimitive(module.isEnabled()));
            moduleObject.add("Drawn", new JsonPrimitive(module.isDrawn()));
            moduleObject.add("Bind", new JsonPrimitive(module.getKeybind().getKeyCode()));

            for (Setting setting : module.getSettings()) 
            {
                if (setting instanceof Checkbox) 
                {
                    settingObject.add(((Checkbox) setting).getName(), new JsonPrimitive(((Checkbox) setting).getValue()));

                    if (((Checkbox) setting).hasSubSettings()) 
                    {
                        for (SubSetting subSetting : ((Checkbox) setting).getSubSettings()) {
//                            if (subSetting instanceof SubCheckbox)
//                                subSettingObject.add(((SubCheckbox) subSetting).getName(), new JsonPrimitive(((SubCheckbox) subSetting).getValue()));
//
//                            if (subSetting instanceof SubSlider)
//                                subSettingObject.add(((SubSlider) subSetting).getName(), new JsonPrimitive(((SubSlider) subSetting).getValue()));
//
//                            if (subSetting instanceof SubMode)
//                                subSettingObject.add(((SubMode) subSetting).getName(), new JsonPrimitive(((SubMode) subSetting).getValue()));
//
//                            if (subSetting instanceof SubKeybind)
//                                subSettingObject.add(((SubKeybind) subSetting).getName(), new JsonPrimitive(((SubKeybind) subSetting).getKey()));

                            if (subSetting instanceof ColorPicker) {
                                JsonObject subColorObject = new JsonObject();

                                subColorObject.add("Red", new JsonPrimitive(((ColorPicker) subSetting).getRed()));
                                subColorObject.add("Green", new JsonPrimitive(((ColorPicker) subSetting).getGreen()));
                                subColorObject.add("Blue", new JsonPrimitive(((ColorPicker) subSetting).getBlue()));
                                subColorObject.add("Alpha", new JsonPrimitive(((ColorPicker) subSetting).getAlpha()));

                                subSettingObject.add(((ColorPicker) subSetting).getName(), subColorObject);
                            }
                        }
                    }
                }

                if (setting instanceof Slider) 
                {
                    settingObject.add(((Slider) setting).getName(), new JsonPrimitive(((Slider) setting).getValue()));

//                    if (((Slider) setting).hasSubSettings()) 
//                    {
//                        for (SubSetting subSetting : ((Slider) setting).getSubSettings()) 
//                        {
//                            if (subSetting instanceof SubCheckbox)
//                                subSettingObject.add(((SubCheckbox) subSetting).getName(), new JsonPrimitive(((SubCheckbox) subSetting).getValue()));
//
//                            if (subSetting instanceof SubSlider)
//                                subSettingObject.add(((SubSlider) subSetting).getName(), new JsonPrimitive(((SubSlider) subSetting).getValue()));
//
//                            if (subSetting instanceof SubMode)
//                                subSettingObject.add(((SubMode) subSetting).getName(), new JsonPrimitive(((SubMode) subSetting).getValue()));
//
//                            if (subSetting instanceof SubKeybind)
//                                subSettingObject.add(((SubKeybind) subSetting).getName(), new JsonPrimitive(((SubKeybind) subSetting).getKey()));
//                        }
//                    }
                }

                if (setting instanceof Mode) 
                {
                    settingObject.add(((Mode) setting).getName(), new JsonPrimitive(((Mode) setting).getValue()));

//                    if (((Mode) setting).hasSubSettings()) 
//                    {
//                        for (SubSetting subSetting : ((Mode) setting).getSubSettings()) 
//                        {
//                            if (subSetting instanceof SubCheckbox)
//                                subSettingObject.add(((SubCheckbox) subSetting).getName(), new JsonPrimitive(((SubCheckbox) subSetting).getValue()));
//
//                            if (subSetting instanceof SubSlider)
//                                subSettingObject.add(((SubSlider) subSetting).getName(), new JsonPrimitive(((SubSlider) subSetting).getValue()));
//
//                            if (subSetting instanceof SubMode)
//                                subSettingObject.add(((SubMode) subSetting).getName(), new JsonPrimitive(((SubMode) subSetting).getValue()));
//
//                            if (subSetting instanceof SubKeybind)
//                                subSettingObject.add(((SubKeybind) subSetting).getName(), new JsonPrimitive(((SubKeybind) subSetting).getKey()));
//                        }
//                    }
                }

                if (setting instanceof Keybind) 
                {
                    settingObject.add(((Keybind) setting).getName(), new JsonPrimitive(((Keybind) setting).getKey()));

//                    if (((Keybind) setting).hasSubSettings()) 
//                    {
//                        for (SubSetting subSetting : ((Keybind) setting).getSubSettings()) 
//                        {
//                            if (subSetting instanceof SubCheckbox)
//                                subSettingObject.add(((SubCheckbox) subSetting).getName(), new JsonPrimitive(((SubCheckbox) subSetting).getValue()));
//
//                            if (subSetting instanceof SubSlider)
//                                subSettingObject.add(((SubSlider) subSetting).getName(), new JsonPrimitive(((SubSlider) subSetting).getValue()));
//
//                            if (subSetting instanceof SubMode)
//                                subSettingObject.add(((SubMode) subSetting).getName(), new JsonPrimitive(((SubMode) subSetting).getValue()));
//
//                            if (subSetting instanceof SubKeybind)
//                                subSettingObject.add(((SubKeybind) subSetting).getName(), new JsonPrimitive(((SubKeybind) subSetting).getKey()));
//                        }
//                    }
                }
            }

            settingObject.add("SubSettings", subSettingObject);
            moduleObject.add("Settings", settingObject);
            String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));
            fileOutputStreamWriter.write(jsonString);
            fileOutputStreamWriter.close();
        }
    }

    @SuppressWarnings("resource")
	public static void loadModules() throws IOException 
    {
        for (Module module : ModuleManager.modules) 
        {
        	String modulePath = "cute/modules/" + module.getName() + ".json";
        	
        	System.out.println("Loading module: " + modulePath);
        	
            if (!Files.exists(Paths.get(modulePath)))
                continue;

            InputStream inputStream = Files.newInputStream(Paths.get(modulePath));
            JsonObject moduleObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

            if (moduleObject.get("Name") == null || 
        		moduleObject.get("Enabled") == null || 
        		moduleObject.get("Drawn") == null || 
        		moduleObject.get("Bind") == null)
                continue;

            JsonObject settingObject    = moduleObject.get("Settings").getAsJsonObject();
            JsonObject subSettingObject = settingObject.get("SubSettings").getAsJsonObject();

            for (Setting setting : module.getSettings()) 
            {
                JsonElement settingValueObject = null;

                if (setting instanceof Checkbox) 
                {
                    settingValueObject = settingObject.get(((Checkbox) setting).getName());

                    for (SubSetting subSetting : ((Checkbox) setting).getSubSettings()) 
                    {
                        JsonElement subSettingValueObject = null;

                        JsonElement redValueObject = null;
                        JsonElement greenValueObject = null;
                        JsonElement blueValueObject = null;
                        JsonElement alphaValueObject = null;

//                        if (subSetting instanceof SubCheckbox)
//                            subSettingValueObject = subSettingObject.get(((SubCheckbox) subSetting).getName());
//
//                        if (subSetting instanceof SubSlider)
//                            subSettingValueObject = subSettingObject.get(((SubSlider) subSetting).getName());
//
//                        if (subSetting instanceof SubMode)
//                            subSettingValueObject = subSettingObject.get(((SubMode) subSetting).getName());
//
//                        if (subSetting instanceof SubKeybind)
//                            subSettingValueObject = subSettingObject.get(((SubKeybind) subSetting).getName());

                        if (subSetting instanceof ColorPicker) 
                        {
                            try 
                            {
                                if (subSettingObject.get(((ColorPicker) subSetting).getName()).getAsJsonObject() == null)
                                    return;

                                JsonObject subColorObject = subSettingObject.get(((ColorPicker) subSetting).getName()).getAsJsonObject();

                                if (subColorObject.get("Red") == null || subColorObject.get("Green") == null || subColorObject.get("Blue") == null || subColorObject.get("Alpha") == null)
                                	return;

                                redValueObject   = subColorObject.get("Red");
                                greenValueObject = subColorObject.get("Green");
                                blueValueObject  = subColorObject.get("Blue");
                                alphaValueObject = subColorObject.get("Alpha");
                            } 
                            catch (Exception ignored) 
                            {

                            }
                        }

//                        if (subSettingValueObject != null) 
//                        {
//                            if (subSetting instanceof SubCheckbox)
//                                ((SubCheckbox) subSetting).setChecked(subSettingValueObject.getAsBoolean());
//
//                            if (subSetting instanceof SubSlider)
//                                ((SubSlider) subSetting).setValue(subSettingValueObject.getAsDouble());
//
//                            if (subSetting instanceof SubMode)
//                                ((SubMode) subSetting).setMode(subSettingValueObject.getAsInt());
//
//                            if (subSetting instanceof SubKeybind)
//                                ((SubKeybind) subSetting).setKey(subSettingValueObject.getAsInt());
//                        }

                        if (redValueObject != null && greenValueObject != null && blueValueObject != null && alphaValueObject != null)
                            ((ColorPicker) subSetting).setColor(new Color(redValueObject.getAsInt(), greenValueObject.getAsInt(), blueValueObject.getAsInt(), alphaValueObject.getAsInt()));
                    }
                }

                if (setting instanceof Slider) 
                {
                    settingValueObject = settingObject.get(((Slider) setting).getName());

                    for (SubSetting subSetting : ((Slider) setting).getSubSettings()) 
                    {
                        JsonElement subSettingValueObject = null;

//                        if (subSetting instanceof SubCheckbox)
//                            subSettingValueObject = subSettingObject.get(((SubCheckbox) subSetting).getName());
//
//                        if (subSetting instanceof SubSlider)
//                            subSettingValueObject = subSettingObject.get(((SubSlider) subSetting).getName());
//
//                        if (subSetting instanceof SubMode)
//                            subSettingValueObject = subSettingObject.get(((SubMode) subSetting).getName());
//
//                        if (subSetting instanceof SubKeybind)
//                            subSettingValueObject = subSettingObject.get(((SubKeybind) subSetting).getName());
//
//                        if (subSettingValueObject != null) 
//                        {
//                            if (subSetting instanceof SubCheckbox)
//                                ((SubCheckbox) subSetting).setChecked(subSettingValueObject.getAsBoolean());
//
//                            if (subSetting instanceof SubSlider)
//                                ((SubSlider) subSetting).setValue(subSettingValueObject.getAsDouble());
//
//                            if (subSetting instanceof SubMode)
//                                ((SubMode) subSetting).setMode(subSettingValueObject.getAsInt());
//
//                            if (subSetting instanceof SubKeybind)
//                                ((SubKeybind) subSetting).setKey(subSettingValueObject.getAsInt());
//                        }
                    }
                }

                if (setting instanceof Mode) 
                {
                    settingValueObject = settingObject.get(((Mode) setting).getName());

                    for (SubSetting subSetting : ((Mode) setting).getSubSettings()) 
                    {
                        JsonElement subSettingValueObject = null;

//                        if (subSetting instanceof SubCheckbox)
//                            subSettingValueObject = subSettingObject.get(((SubCheckbox) subSetting).getName());
//
//                        if (subSetting instanceof SubSlider)
//                            subSettingValueObject = subSettingObject.get(((SubSlider) subSetting).getName());
//
//                        if (subSetting instanceof SubMode)
//                            subSettingValueObject = subSettingObject.get(((SubMode) subSetting).getName());
//
//                        if (subSetting instanceof SubKeybind)
//                            subSettingValueObject = subSettingObject.get(((SubKeybind) subSetting).getName());
//
//                        if (subSettingValueObject != null) 
//                        {
//                            if (subSetting instanceof SubCheckbox)
//                                ((SubCheckbox) subSetting).setChecked(subSettingValueObject.getAsBoolean());
//
//                            if (subSetting instanceof SubSlider)
//                                ((SubSlider) subSetting).setValue(subSettingValueObject.getAsDouble());
//
//                            if (subSetting instanceof SubMode)
//                                ((SubMode) subSetting).setMode(subSettingValueObject.getAsInt());
//
//                            if (subSetting instanceof SubKeybind)
//                                ((SubKeybind) subSetting).setKey(subSettingValueObject.getAsInt());
//                        }
                    }
                }

                if (setting instanceof Keybind) 
                {
                    settingValueObject = settingObject.get(((Keybind) setting).getName());

                    for (SubSetting subSetting : ((Keybind) setting).getSubSettings()) {
                        JsonElement subSettingValueObject = null;

//                        if (subSetting instanceof SubCheckbox)
//                            subSettingValueObject = subSettingObject.get(((SubCheckbox) subSetting).getName());
//
//                        if (subSetting instanceof SubSlider)
//                            subSettingValueObject = subSettingObject.get(((SubSlider) subSetting).getName());
//
//                        if (subSetting instanceof SubMode)
//                            subSettingValueObject = subSettingObject.get(((SubMode) subSetting).getName());
//
//                        if (subSetting instanceof SubKeybind)
//                            subSettingValueObject = subSettingObject.get(((SubKeybind) subSetting).getName());
//
//                        if (subSettingValueObject != null) 
//                        {
//                            if (subSetting instanceof SubCheckbox)
//                                ((SubCheckbox) subSetting).setChecked(subSettingValueObject.getAsBoolean());
//
//                            if (subSetting instanceof SubSlider)
//                                ((SubSlider) subSetting).setValue(subSettingValueObject.getAsDouble());
//
//                            if (subSetting instanceof SubMode)
//                                ((SubMode) subSetting).setMode(subSettingValueObject.getAsInt());
//
//                            if (subSetting instanceof SubKeybind)
//                                ((SubKeybind) subSetting).setKey(subSettingValueObject.getAsInt());
//                        }
                    }
                }

                if (settingValueObject != null) 
                {
                    if (setting instanceof Checkbox)
                        ((Checkbox) setting).setChecked(settingValueObject.getAsBoolean());

                    if (setting instanceof Slider)
                        ((Slider) setting).setValue(settingValueObject.getAsDouble());

                    if (setting instanceof Mode)
                        ((Mode) setting).setMode(settingValueObject.getAsInt());

                    if (setting instanceof Keybind)
                        ((Keybind) setting).setKey(settingValueObject.getAsInt());
                }
            }

            module.setEnabled(moduleObject.get("Enabled").getAsBoolean());
            module.setDrawn(moduleObject.get("Drawn").getAsBoolean());
            module.setKeyCode(moduleObject.get("Bind").getAsInt());
        }
    }
    
    public static void saveBlockList() throws IOException
    {
    	registerFiles("Virtual Block List", "modules");
        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("cute/modules/Virtual Block List.json"), StandardCharsets.UTF_8);

        JsonArray moduleObject = new JsonArray();
        
        for(VirtualBlock b : VirtualBlock.Blocks) 
        {
        	JsonObject jo = new JsonObject();
        	jo.add("Id",  new JsonPrimitive(b.id));
        	jo.add("Red",  new JsonPrimitive(b.r));
        	jo.add("Green",  new JsonPrimitive(b.g));
        	jo.add("Blue",  new JsonPrimitive(b.b));
        	jo.add("Alpha",  new JsonPrimitive(b.a));
        	jo.add("Meta",  new JsonPrimitive(b.meta));
        	jo.add("Enabled",  new JsonPrimitive(b.enabled));
        	
        	moduleObject.add(jo);
        }
        
        String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }

    
    public static void LoadBlockList() throws IOException 
    {
    	if (!Files.exists(Paths.get("cute/modules/Virtual Block List.json")))
            return;

        InputStream inputStream = Files.newInputStream(Paths.get("cute/modules/Virtual Block List.json"));
        JsonArray blockListObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonArray();

        for (int i = 0; i < blockListObject.size(); i++) 
        {
        	JsonObject jsonObj = blockListObject.get(i).getAsJsonObject();
        	
        	VirtualBlock vb = new VirtualBlock();
        	
        	vb.id = jsonObj.get("Id").getAsString();
            vb.r = jsonObj.get("Red").getAsInt();
            vb.g = jsonObj.get("Green").getAsInt();
            vb.b = jsonObj.get("Blue").getAsInt();
            vb.a = jsonObj.get("Alpha").getAsInt();
            vb.meta = jsonObj.get("Meta").getAsInt();
            vb.enabled = jsonObj.get("Enabled").getAsBoolean();
            
            VirtualBlock.registerBlock(vb);
        }
    }
//    public static void saveGUI() throws IOException 
//    {
//        registerFiles("GUI", "gui");
//
//        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("cute/gui/GUI.json"), StandardCharsets.UTF_8);
//        JsonObject guiObject = new JsonObject();
//        JsonObject windowObject = new JsonObject();
//
//        for (Window window : Window.windows) 
//        {
//            JsonObject positionObject = new JsonObject();
//
//            positionObject.add("x", new JsonPrimitive(window.x));
//            positionObject.add("y", new JsonPrimitive(window.y));
//            positionObject.add("open", new JsonPrimitive(window.opened));
//
//            windowObject.add(window.category.getName(), positionObject);
//        }
//
//        guiObject.add("Windows", windowObject);
//        String jsonString = gson.toJson(new JsonParser().parse(guiObject.toString()));
//        fileOutputStreamWriter.write(jsonString);
//        fileOutputStreamWriter.close();
//    }
//
//    public static void loadGUI() throws IOException 
//    {
//        if (!Files.exists(Paths.get("cute/gui/GUI.json")))
//            return;
//
//        InputStream inputStream = Files.newInputStream(Paths.get("cute/gui/GUI.json"));
//        JsonObject guiObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();
//
//        if (guiObject.get("Windows") == null)
//            return;
//
//        JsonObject windowObject = guiObject.get("Windows").getAsJsonObject();
//        for (Window window : Window.windows) 
//        {
//            if (windowObject.get(window.category.name()) == null)
//                return;
//
//            JsonObject categoryObject = windowObject.get(window.category.name()).getAsJsonObject();
//
//            JsonElement windowXObject = categoryObject.get("x");
//            if (windowXObject != null && windowXObject.isJsonPrimitive())
//                window.x = windowXObject.getAsInt();
//
//            JsonElement windowYObject = categoryObject.get("y");
//            if (windowYObject != null && windowYObject.isJsonPrimitive())
//                window.y = windowYObject.getAsInt();
//
//            JsonElement windowOpenObject = categoryObject.get("open");
//            if (windowOpenObject != null && windowOpenObject.isJsonPrimitive())
//                window.opened = windowOpenObject.getAsBoolean();
//        }
//
//        inputStream.close();
//    }

//    public static void saveHUD() throws IOException 
//    {
//        registerFiles("HUD", "gui");
//
//        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("cute/gui/HUD.json"), StandardCharsets.UTF_8);
//        JsonObject guiObject = new JsonObject();
//        JsonObject hudObject = new JsonObject();
//
//        for (HUDComponent component : HUDComponentManager.getComponents()) 
//        {
//            JsonObject positionObject = new JsonObject();
//
//            positionObject.add("x", new JsonPrimitive(component.x));
//            positionObject.add("y", new JsonPrimitive(component.y));
//            positionObject.add("enabled", new JsonPrimitive(component.isEnabled()));
//
//            hudObject.add(component.getName(), positionObject);
//        }
//
//        guiObject.add("Components", hudObject);
//        String jsonString = gson.toJson(new JsonParser().parse(guiObject.toString()));
//        fileOutputStreamWriter.write(jsonString);
//        fileOutputStreamWriter.close();
//    }
//
//    public static void loadHUD() throws IOException 
//    {
//        if (!Files.exists(Paths.get("cute/gui/HUD.json")))
//            return;
//
//        InputStream inputStream = Files.newInputStream(Paths.get("cute/gui/HUD.json"));
//        JsonObject guiObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();
//
//        if (guiObject.get("Components") == null)
//            return;
//
//        JsonObject windowObject = guiObject.get("Components").getAsJsonObject();
//        for (HUDComponent component : HUDComponentManager.getComponents()) 
//        {
//            if (windowObject.get(component.getName()) == null)
//                return;
//
//            JsonObject categoryObject = windowObject.get(component.getName()).getAsJsonObject();
//
//            JsonElement hudXObject = categoryObject.get("x");
//            if (hudXObject != null && hudXObject.isJsonPrimitive())
//                component.x = hudXObject.getAsInt();
//
//            JsonElement hudYObject = categoryObject.get("y");
//            if (hudYObject != null && hudYObject.isJsonPrimitive())
//                component.y = hudYObject.getAsInt();
//
//            JsonElement hudEnabledObject = categoryObject.get("enabled");
//            if (hudEnabledObject != null && hudEnabledObject.isJsonPrimitive())
//                if (hudEnabledObject.getAsBoolean())
//                    component.toggle();
//        }
//
//        inputStream.close();
//    }

//    public static void saveFriends() throws IOException 
//    {
//        registerFiles("Friends", "Social");
//
//        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("cute/social/Friends.json"), StandardCharsets.UTF_8);
//        JsonObject mainObject = new JsonObject();
//        JsonArray friendArray = new JsonArray();
//
//        for (Friend friend : FriendManager.getFriends()) 
//        {
//            friendArray.add(friend.getName());
//        }
//
//        mainObject.add("Friends", friendArray);
//        String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
//        fileOutputStreamWriter.write(jsonString);
//        fileOutputStreamWriter.close();
//    }
//
//    public static void loadFriends() throws IOException 
//    {
//        if (!Files.exists(Paths.get("cute/social/Friends.json")))
//            return;
//
//        InputStream inputStream = Files.newInputStream(Paths.get("cute/social/Friends.json"));
//        JsonObject mainObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();
//
//        if (mainObject.get("Friends") == null)
//            return;
//
//        JsonArray friendObject = mainObject.get("Friends").getAsJsonArray();
//
//        friendObject.forEach(object -> FriendManager.addFriend(object.getAsString()));
//
//        inputStream.close();
//    }

//    public static void saveEnemies() throws IOException 
//    {
//        registerFiles("Enemies", "social");
//
//        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("cute/social/Enemies.json"), StandardCharsets.UTF_8);
//        JsonObject mainObject = new JsonObject();
//        JsonArray enemyArray = new JsonArray();
//
//        for (Enemy enemy : EnemyManager.getEnemies()) 
//        {
//            enemyArray.add(enemy.getName());
//        }
//
//        mainObject.add("Enemies", enemyArray);
//        String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
//        fileOutputStreamWriter.write(jsonString);
//        fileOutputStreamWriter.close();
//    }
//
//    public static void loadEnemies() throws IOException 
//    {
//        if (!Files.exists(Paths.get("cute/social/Enemies.json")))
//            return;
//
//        InputStream inputStream = Files.newInputStream(Paths.get("cute/social/Enemies.json"));
//        JsonObject mainObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();
//
//        if (mainObject.get("Enemies") == null)
//            return;
//
//        JsonArray enemyObject = mainObject.get("Enemies").getAsJsonArray();
//
//        enemyObject.forEach(object -> EnemyManager.addEnemy(object.getAsString()));
//
//        inputStream.close();
//    }
}
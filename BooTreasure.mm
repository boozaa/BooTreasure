<map version="0.9.0">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1388988815279" ID="ID_1707873807" MODIFIED="1388988823435" TEXT="BooTreasure">
<node CREATED="1388988825250" ID="ID_1638559445" MODIFIED="1388991308048" POSITION="right">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <b>public abstract class Treasure</b>&#160;&#160;implements <b>Serializable</b>
    </p>
    <p>
      
    </p>
    <p>
      &#160;&#160;&#160;&#160;@Getter <b>protected transient <font color="#3333ff">TreasureType</font></b>&#160; typ<b>e</b>;
    </p>
    <p>
      &#160;&#160;&#160;&#160;@Getter @Setter <b>protected transient <font color="#3333ff">ConfigurationSection</font></b>&#160; configSection;
    </p>
    <p>
      &#160;&#160;&#160;&#160;@Getter @Setter <b>protected transient <font color="#3333ff">Long</font></b>&#160; duration;
    </p>
    <p>
      &#160;&#160;&#160;&#160;@Getter @Setter <b>protected transient <font color="#3333ff">String</font></b>&#160; cronPattern, taskId;
    </p>
    <p>
      <font color="#009966">&#160;&#160;&#160; <b>// Ces propri&#233;t&#233;s seront s&#233;rialis&#233;es</b></font>
    </p>
    <p>
      &#160;&#160;&#160;&#160;@Getter @Setter <b>protected <font color="#3333ff">String</font></b>&#160;id, name, world, serializedPath;
    </p>
    <p>
      &#160;&#160;&#160;&#160;@Getter @Setter <b>protected <font color="#3333ff">Boolean</font>&#160;</b>isInfinite, isOnlyOnSurface, isFound;
    </p>
    <p>
      
    </p>
    <p>
      
    </p>
    <p>
      &#160;&#160;&#160; <b>public</b>&#160;Treasure( TreasureType type)
    </p>
    <p>
      &#160;&#160;&#160; <b>public</b>&#160;Treasure( TreasureType&#160;type, ConfigurationSection section )
    </p>
    <p>
      &#160;&#160;&#160; <b>public</b>&#160;Treasure( TreasureType type, Location loc )
    </p>
    <p>
      &#160;&#160;&#160; <b>public</b>&#160;Treasure( TreasureType type, File file )
    </p>
    <p>
      
    </p>
    <p>
      &#160;&#160;&#160; <font color="#009966"><b>// Serialization functions</b></font>
    </p>
    <p>
      &#160;&#160;&#160; <b>public void</b>&#160;serialize() throws Exception;
    </p>
    <p>
      &#160;&#160;&#160; <b>public</b>&#160;<b>Treasure</b>&#160;unserialize() throws Exception;
    </p>
    <p>
      &#160;&#160;&#160; <b>public Treasure</b>&#160;unserialize(File f) throws Exception;
    </p>
    <p>
      &#160;&#160;&#160; <b>public void</b>&#160;deleteSerializedFile() throws Exception;
    </p>
    <p>
      
    </p>
    <p>
      
    </p>
    <p>
      &#160;&#160;&#160; <font color="#009966"><b>// Messages functions</b></font>
    </p>
    <p>
      &#160;&#160;&#160; <b>public void</b>&#160;announceAppear()
    </p>
    <p>
      &#160;&#160;&#160; <b>public void</b>&#160;announceFound()
    </p>
    <p>
      &#160;&#160;&#160; <b>public void</b>&#160;announceFoundButNotEmpty()
    </p>
    <p>
      &#160;&#160;&#160; <b>public void</b>&#160;announceDisAppear()
    </p>
    <p>
      
    </p>
    <p>
      
    </p>
    <p>
      &#160;&#160;&#160;<b>&#160;<font color="#009966">// Commons functions</font></b>
    </p>
    <p>
      &#160;&#160;&#160; <b>protected abstract void</b>&#160;generateTreasure();
    </p>
    <p>
      &#160;&#160;&#160; <b>protected abstract String</b>&#160;replaceVariables( String msg );
    </p>
    <p>
      &#160;&#160;&#160; <b>public abstract void</b>&#160;appear()&#160;throws Exception;
    </p>
    <p>
      &#160;&#160;&#160; <b>public abstract void</b>&#160;disappear() throws Exception;
    </p>
    <p>
      &#160;&#160;&#160; <b>public abstract void</b>&#160;found(Player p);
    </p>
  </body>
</html>
</richcontent>
<node CREATED="1388989913655" HGAP="180" ID="ID_1442692810" MODIFIED="1388991314622" VSHIFT="-60">
<richcontent TYPE="NODE"><html>
  <head>
    
  </head>
  <body>
    <p>
      <b>public class</b>&#160;TreasureChest extends <b>Treasure </b>
    </p>
    <p>
      
    </p>
    <p>
      <b>&#160;&#160;&#160; </b>@Getter&#160;@Setter <b>private transient</b>&#160;<font color="#3333ff"><b>ItemStack[]</b></font>&#160; inventory;
    </p>
    <p>
      &#160;&#160;&#160;&#160;@Getter @Setter <b>protected transient <font color="#3333ff">List&lt;Material&gt;</font></b>&#160; placesMaterials;
    </p>
    <p>
      &#160;&#160;&#160;&#160;@Getter <b>private transient <font color="#3333ff">Block</font></b>&#160;block;
    </p>
    <p>
      &#160;&#160;&#160; <font color="#009966"><b>// Ces propri&#233;t&#233;s seront s&#233;rialis&#233;es</b></font>
    </p>
    <p>
      &#160;&#160;&#160;&#160;@Getter <b>private <font color="#3333ff">int</font></b>&#160;x, y, z;
    </p>
    <p>
      &#160;&#160;&#160;&#160;@Getter @Setter <b>private&#160;<font color="#3333ff">Boolean</font></b>&#160; isPreserveContent;&#160;&#160;&#160;
    </p>
    <p>
      
    </p>
    <p>
      
    </p>
    <p>
      &#160;&#160;&#160; <b>public</b>&#160;TreasureChest();
    </p>
    <p>
      &#160;&#160;&#160; <b>public</b>&#160;TreasureChest( ConfigurationSection section );
    </p>
    <p>
      &#160;&#160;&#160; <b>public</b>&#160;TreasureChest( Location loc );
    </p>
    <p>
      &#160;&#160;&#160; <b>public</b>&#160;TreasureChest( File file );
    </p>
    <p>
      
    </p>
    <p>
      &#160;&#160;&#160;&#160;@Override <b>protected void</b>&#160;generateTreasure();
    </p>
    <p>
      &#160;&#160;&#160; <b>public void</b>&#160;getContentsFromChest();
    </p>
    <p>
      
    </p>
    <p>
      
    </p>
    <p>
      &#160;&#160;&#160; <b>public void</b>&#160;chestAppear();
    </p>
    <p>
      &#160;&#160;&#160; <b>public void</b>&#160;chestDisappear();
    </p>
    <p>
      &#160;&#160;&#160;&#160;@Override <b>public void</b>&#160;appear();
    </p>
    <p>
      &#160;&#160;&#160;&#160;@Override <b>public void</b>&#160;disappear();
    </p>
    <p>
      &#160;&#160;&#160;&#160;@Override <b>public void</b>&#160;found(Player p);
    </p>
    <p>
      &#160;&#160;&#160;
    </p>
    <p>
      &#160;&#160;&#160;&#160;@Override <b>protected String</b>&#160;replaceVariables( String msg );
    </p>
    <p>
      &#160;&#160;&#160;&#160;@Override <b>public String</b>&#160;toString():
    </p>
  </body>
</html>
</richcontent>
</node>
</node>
</node>
</map>

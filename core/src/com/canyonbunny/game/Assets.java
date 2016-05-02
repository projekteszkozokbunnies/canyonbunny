package com.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();
    private AssetManager assetManager;

    private Assets () {}

    public AssetBunny bunny;
    public AssetRock rock;
    public AssetGoldCoin goldCoin;
    public AssetFeather feather;
    public AssetLevelDecoration levelDecoration;

    public AssetFonts fonts;

    /**
     * A játékban megjelenő betűk létrehozására szolgál.  
     */
    public class AssetFonts {
        public final BitmapFont defaultSmall;
        public final BitmapFont defaultNormal;
        public final BitmapFont defaultBig;

        /**
         * Konstruktor, mely beállítja a betűk méretét és a szép megjelenéshez szükséges textúra szűrés módját.
         */
        public AssetFonts () {
            defaultSmall = new BitmapFont(
                    Gdx.files.internal("img/arial-15.fnt"), true);
            defaultNormal = new BitmapFont(
                    Gdx.files.internal("img/arial-15.fnt"), true);
            defaultBig = new BitmapFont(
                    Gdx.files.internal("img/arial-15.fnt"), true);
            defaultSmall.getData().setScale(0.75f, 0.75f);
            defaultNormal.getData().setScale(1.0f, 1.0f);
            defaultBig.getData().setScale(1.5f, 1.5f);
            defaultSmall.getRegion().getTexture().setFilter(
                    TextureFilter.Linear, TextureFilter.Linear);
            defaultNormal.getRegion().getTexture().setFilter(
                    TextureFilter.Linear, TextureFilter.Linear);
            defaultBig.getRegion().getTexture().setFilter(
                    TextureFilter.Linear, TextureFilter.Linear);
        }
    }

    /**
     * Inicializálja az AssetManager-t. Betölti az atlas-t, ami a játékban megjelenő textúrákhoz fog kelleni. 
     * Ezután a fontok és a különböző pályaelemek létrehozása történik.
     */
    public void init (AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(com.canyonbunny.game.util.Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames())
            Gdx.app.debug(TAG, "asset: " + a);

        TextureAtlas atlas = assetManager.get(com.canyonbunny.game.util.Constants.TEXTURE_ATLAS_OBJECTS);
        for (Texture t : atlas.getTextures()) {
            t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
        fonts = new AssetFonts();
        bunny = new AssetBunny(atlas);
        rock = new AssetRock(atlas);
        goldCoin = new AssetGoldCoin(atlas);
        feather = new AssetFeather(atlas);
        levelDecoration = new AssetLevelDecoration(atlas);
    }

    /**
     * Meghíváskor elengedi az erőforrásokat.
     */
    @Override
    public void dispose () {
        assetManager.dispose();
        fonts.defaultSmall.dispose();
        fonts.defaultNormal.dispose();
        fonts.defaultBig.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" +
                asset.fileName + "'", (Exception)throwable);
    }

    /**
     * Az erőforrások csökkentése céljából a játékelemeket csak egyszer töltjük be memóriába.
     * Ebben az osztályban tároljuk el a nyulat. 
     */
    public class AssetBunny {

        public final AtlasRegion head;

        public AssetBunny(TextureAtlas atlas) {
            head = atlas.findRegion("bunny_head");
        }

    }

    /**
     * A sziklák tárolása itt történik.
     */
    public class AssetRock {

        public final AtlasRegion edge;
        public final AtlasRegion middle;

        public AssetRock(TextureAtlas atlas) {
            edge = atlas.findRegion("rock_edge");
            middle = atlas.findRegion("rock_middle");
        }

    }

    /**
     * A felvehető érme elhelyzésére szolgál.
     */
    public class AssetGoldCoin {

        public final AtlasRegion goldCoin;

        public AssetGoldCoin(TextureAtlas atlas) {
            goldCoin = atlas.findRegion("item_gold_coin");
        }

    }

    /**
     * A toll tárolására szolgáló osztály.
     */
    public class AssetFeather {

        public final AtlasRegion feather;

        public AssetFeather(TextureAtlas atlas) {
            feather = atlas.findRegion("item_feather");
        }

    }

    /**
     * A pályadekorációhoz tartozó elemek inicializálására szolgáló osztály.
     */
    public class AssetLevelDecoration {
        public final AtlasRegion cloud01;
        public final AtlasRegion cloud02;
        public final AtlasRegion cloud03;
        public final AtlasRegion mountainLeft;
        public final AtlasRegion mountainRight;
        public final AtlasRegion waterOverlay;
        public final AtlasRegion carrot;
        public final AtlasRegion goal;

        /**
         * Konstruktor, mely létrehozza a felhőket, hegyeket, vízet, répát és a célt. 
         * @param atlas Ebből tölti be a szükséges részeket a memóriába.
         */
        public AssetLevelDecoration(TextureAtlas atlas) {
            cloud01 = atlas.findRegion("cloud01");
            cloud02 = atlas.findRegion("cloud02");
            cloud03 = atlas.findRegion("cloud03");
            mountainLeft = atlas.findRegion("mountain_left");
            mountainRight = atlas.findRegion("mountain_right");
            waterOverlay = atlas.findRegion("water_overlay");
            carrot = atlas.findRegion("carrot");
            goal = atlas.findRegion("goal");
        }

    }

}

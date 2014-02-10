package info.fisherevans.ae.ac;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.particle.ParticleSystem;
import org.anddev.andengine.entity.particle.emitter.CircleOutlineParticleEmitter;
import org.anddev.andengine.entity.particle.initializer.ColorInitializer;
import org.anddev.andengine.entity.particle.initializer.RotationInitializer;
import org.anddev.andengine.entity.particle.initializer.VelocityInitializer;
import org.anddev.andengine.entity.particle.modifier.ExpireModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class AndEngineDemoActivity extends BaseGameActivity
{
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 450;

	private Camera camera;
	private BitmapTextureAtlas faceBit;
	private TextureRegion faceText;

	private BitmapTextureAtlas analogTopBit;
	private TextureRegion analogTopText;
	private BitmapTextureAtlas analogBotBit;
	private TextureRegion analogBotText;

	private BitmapTextureAtlas okBit;
	private TextureRegion okText;
	private BitmapTextureAtlas resetBit;
	private TextureRegion resetText;

	private BitmapTextureAtlas partBit;
	private TextureRegion partText;

	public Engine onLoadEngine()
	{
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera));
	}

	public void onLoadResources()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		faceBit = new BitmapTextureAtlas(32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		faceText = BitmapTextureAtlasTextureRegionFactory.createFromAsset(faceBit, this, "face_box.png", 0, 0);
		
		analogTopBit = new BitmapTextureAtlas(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		analogTopText = BitmapTextureAtlasTextureRegionFactory.createFromAsset(analogTopBit, this, "onscreen_control_knob.png", 0, 0);
		analogBotBit = new BitmapTextureAtlas(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		analogBotText = BitmapTextureAtlasTextureRegionFactory.createFromAsset(analogBotBit, this, "onscreen_control_base.png", 0, 0);
		
		okBit = new BitmapTextureAtlas(256, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		okText = BitmapTextureAtlasTextureRegionFactory.createFromAsset(okBit, this, "menu_ok.png", 0, 0);
		resetBit = new BitmapTextureAtlas(256, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		resetText = BitmapTextureAtlasTextureRegionFactory.createFromAsset(resetBit, this, "menu_reset.png", 0, 0);

		partBit = new BitmapTextureAtlas(32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		partText = BitmapTextureAtlasTextureRegionFactory.createFromAsset(partBit, this, "particle_point.png", 0, 0);
		
		mEngine.getTextureManager().loadTexture(faceBit);
		mEngine.getTextureManager().loadTextures(analogTopBit, analogBotBit);
		mEngine.getTextureManager().loadTextures(okBit, resetBit);
		mEngine.getTextureManager().loadTexture(partBit);
	}

	public Scene onLoadScene()
	{
		mEngine.registerUpdateHandler(new FPSLogger());

		Scene scene = new Scene();
		scene.setBackground(new ColorBackground(0.3f, 0.55f, 1));

		int centerX = (CAMERA_WIDTH - faceText.getWidth()) / 2;
		int centerY = (CAMERA_HEIGHT - faceText.getHeight()) / 2;
		final Sprite face = new Sprite(centerX, centerY, faceText)
		{
			public boolean onAreaTouched(TouchEvent touchEvent, float touchX, float touchY)
			{
				setPosition(touchEvent.getX() - getWidth() / 2, touchEvent.getY() - getHeight() / 2);
				return true;
			}
		};
		final PhysicsHandler facePhys = new PhysicsHandler(face);
		face.registerUpdateHandler(facePhys);
		scene.attachChild(face);
		scene.registerTouchArea(face);

		AnalogOnScreenControl analogControl = 
				new AnalogOnScreenControl(40, CAMERA_HEIGHT - analogBotText.getHeight() - 40,
				camera, analogBotText, analogTopText,
				0.1f, 200, new IAnalogOnScreenControlListener()
		{
			public void onControlChange(BaseOnScreenControl baseControl, float anaX, float anaY)
			{
				facePhys.setVelocity(anaX*150, anaY*150);
			}
			public void onControlClick(AnalogOnScreenControl arg0)
			{
				face.registerEntityModifier(
					new SequenceEntityModifier(
						new ScaleModifier(0.5f, 1, 3),
						new RotationModifier(0.5f, 0, 180),
						new ScaleModifier(1, 3, 3),
						new ParallelEntityModifier(
							new ScaleModifier(0.5f, 3, 1),
							new RotationModifier(0.5f, 180, 360))
						)
					);
			}
		});
		scene.setChildScene(analogControl);


		Sprite ok = new Sprite(100, 20, okText)
		{
			public boolean onAreaTouched(TouchEvent touchEvent, float touchX, float touchY)
			{
				face.setSize(face.getWidth() + 15, face.getHeight() + 15);
				return true;
			}
		};
		scene.attachChild(ok);
		scene.registerTouchArea(ok);

		Sprite reset = new Sprite(400, 20, resetText)
		{
			public boolean onAreaTouched(TouchEvent touchEvent, float touchX, float touchY)
			{
				face.setSize(face.getBaseWidth(), face.getBaseHeight());
				return true;
			}
		};
		scene.attachChild(reset);
		scene.registerTouchArea(reset);
		


		final CircleOutlineParticleEmitter particleEmitter = new CircleOutlineParticleEmitter(-100, -100, 40);
		final ParticleSystem particleSystem = new ParticleSystem(particleEmitter, 120, 120, 30, partText);

		scene.setOnSceneTouchListener(new IOnSceneTouchListener()
		{
			public boolean onSceneTouchEvent(Scene scene, TouchEvent touchEvent)
			{
				particleEmitter.setCenter(touchEvent.getX(), touchEvent.getY());
				return true;
			}
		});

		particleSystem.addParticleInitializer(new ColorInitializer(1, 1, 0));
		particleSystem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		particleSystem.addParticleInitializer(new VelocityInitializer(-50, 50, -50, 50));
		particleSystem.addParticleInitializer(new RotationInitializer(0, 180));

		particleSystem.addParticleModifier(new org.anddev.andengine.entity.particle.modifier.ScaleModifier(0.5f, 1, 0, 0.5f));
		particleSystem.addParticleModifier(new org.anddev.andengine.entity.particle.modifier.ScaleModifier(1, 0, 0.5f, 1f));
		particleSystem.addParticleModifier(new ExpireModifier(1, 2));

		scene.attachChild(particleSystem);
		
		
		scene.setTouchAreaBindingEnabled(true);
		return scene;
	}

	public void onLoadComplete() { }
	
}

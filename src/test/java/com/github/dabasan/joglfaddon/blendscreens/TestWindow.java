package com.github.dabasan.joglfaddon.blendscreens;

import static com.github.dabasan.basis.vector.VectorFunctions.*;
import static com.github.dabasan.joglf.gl.wrapper.GLWrapper.*;
import static com.jogamp.opengl.GL.*;

import com.github.dabasan.joglf.gl.front.CameraFront;
import com.github.dabasan.joglf.gl.model.Model3DFunctions;
import com.github.dabasan.joglf.gl.util.screen.Screen;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;

class TestWindow extends JOGLFWindow {
	private int model_handle_a;
	private int model_handle_b;

	private Screen screen_a;
	private Screen screen_b;
	private Screen screen_dst;

	private BlendScreens blend_screens;

	@Override
	public void Init() {
		model_handle_a = Model3DFunctions.LoadModel("./Data/Model/BD1/Cube/cube.bd1");
		model_handle_b = Model3DFunctions.LoadModel("./Data/Model/OBJ/Teapot/teapot.obj");
		Model3DFunctions.TranslateModel(model_handle_b, VGet(0.0f, -10.0f, 0.0f));

		screen_a = new Screen(1024, 1024);
		screen_b = new Screen(1024, 1024);
		screen_dst = new Screen(1024, 1024);

		blend_screens = new BlendScreens();

		glDisable(GL_CULL_FACE);
	}

	@Override
	public void Update() {
		CameraFront.SetCameraPositionAndTarget_UpVecY(VGet(30.0f, 30.0f, 30.0f),
				VGet(0.0f, 0.0f, 0.0f));
	}

	@Override
	public void Draw() {
		this.DrawToScreenA();
		this.DrawToScreenB();

		screen_dst.Enable();
		screen_dst.Clear();
		screen_dst.Disable();

		blend_screens.Overlay(screen_a, screen_b, screen_dst);

		screen_dst.Draw(0, 0, this.GetWidth(), this.GetHeight());
	}
	private void DrawToScreenA() {
		screen_a.Enable();
		screen_a.Clear();
		Model3DFunctions.DrawModel(model_handle_a);
		screen_a.Disable();
	}
	private void DrawToScreenB() {
		screen_b.Enable();
		screen_b.Clear();
		Model3DFunctions.DrawModel(model_handle_b);
		screen_b.Disable();
	}
}

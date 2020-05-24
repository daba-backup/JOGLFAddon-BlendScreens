package com.github.dabasan.joglfaddon.blendscreens;

import static com.github.dabasan.joglf.gl.wrapper.GLWrapper.*;
import static com.jogamp.opengl.GL.*;

import com.github.dabasan.joglf.gl.shader.ShaderProgram;
import com.github.dabasan.joglf.gl.transferrer.FullscreenQuadTransferrerWithUV;
import com.github.dabasan.joglf.gl.util.screen.Screen;

/**
 * Blends screens.
 * 
 * @author Daba
 *
 */
public class BlendScreens {
	private final ShaderProgram program;
	private final FullscreenQuadTransferrerWithUV transferrer;

	private static final int OPERATION_ADD = 1;
	private static final int OPERATION_SUB = 2;
	private static final int OPERATION_MUL = 3;
	private static final int OPERATION_OVERLAY = 4;

	public BlendScreens() {
		program = new ShaderProgram("blend_screens",
				"./Data/Shader/330/addon/dabasan/blend_screens/vshader.glsl",
				"./Data/Shader/330/addon/dabasan/blend_screens/fshader.glsl");
		transferrer = new FullscreenQuadTransferrerWithUV(true);
	}

	/**
	 * A + B
	 * 
	 * @param a
	 *            Screen A
	 * @param b
	 *            Screen B
	 * @param dst
	 *            Destination screen
	 */
	public void Add(Screen a, Screen b, Screen dst) {
		program.Enable();

		program.SetUniform("operation", OPERATION_ADD);
		this.SetTextures(a, b);

		dst.Enable();
		transferrer.Transfer();
		dst.Disable();

		program.Disable();
	}
	/**
	 * A - B
	 * 
	 * @param a
	 *            Screen A
	 * @param b
	 *            Screen B
	 * @param dst
	 *            Destination screen
	 */
	public void Sub(Screen a, Screen b, Screen dst) {
		program.Enable();

		program.SetUniform("operation", OPERATION_SUB);
		this.SetTextures(a, b);

		dst.Enable();
		transferrer.Transfer();
		dst.Disable();

		program.Disable();
	}
	/**
	 * A * B
	 * 
	 * @param a
	 *            Screen A
	 * @param b
	 *            Screen B
	 * @param dst
	 *            Destination screen
	 */
	public void Mul(Screen a, Screen b, Screen dst) {
		program.Enable();

		program.SetUniform("operation", OPERATION_MUL);
		this.SetTextures(a, b);

		dst.Enable();
		transferrer.Transfer();
		dst.Disable();

		program.Disable();
	}
	/**
	 * Overlay
	 * 
	 * @param foreground
	 *            Foreground screen
	 * @param background
	 *            Background screen
	 * @param dst
	 *            Destination screen
	 */
	public void Overlay(Screen foreground, Screen background, Screen dst) {
		program.Enable();

		program.SetUniform("operation", OPERATION_OVERLAY);
		this.SetTextures(foreground, background);

		dst.Enable();
		transferrer.Transfer();
		dst.Disable();

		program.Disable();
	}
	private void SetTextures(Screen a, Screen b) {
		glActiveTexture(GL_TEXTURE0);
		a.BindScreenTexture();
		program.SetUniform("texture_0", 0);

		glActiveTexture(GL_TEXTURE1);
		b.BindScreenTexture();
		program.SetUniform("texture_1", 1);
	}
}

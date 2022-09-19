package main.java.graphics;

import javafx.scene.image.*;

/**
 * Lưu trữ thông tin các pixel của 1 sprite (hình ảnh game)
 */
public class Sprite {
	
	public static final int DEFAULT_SIZE = 16;
	public static final int SCALE = 5;
	public static final int SCALED_SIZE = DEFAULT_SIZE * SCALE;
    private static final int TRANSPARENT_COLOR = 0xffff00ff;
	public final int SIZE;
	private int _x, _y;
	public int[] _pixels;
	protected int _realWidth;
	protected int _realHeight;
	private SpriteSheet _sheet;

	/*
	|--------------------------------------------------------------------------
	| Board sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite background = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.tiles, 16, 16);
	public static Sprite wall_hor = new Sprite(DEFAULT_SIZE, 1, 0, SpriteSheet.tiles, 16, 5);
	public static Sprite wall_ver = new Sprite(DEFAULT_SIZE, 2, 0, SpriteSheet.tiles, 5, 16);
	public static Sprite wall_mid = new Sprite(DEFAULT_SIZE, 3, 0, SpriteSheet.tiles, 6, 16);

	/*
	|--------------------------------------------------------------------------
	| Player Sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite player = new Sprite(DEFAULT_SIZE, 0, 1, SpriteSheet.tiles, 10, 12);

	/*
	|--------------------------------------------------------------------------
	| Ball Sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite ball = new Sprite(DEFAULT_SIZE, 1, 1, SpriteSheet.tiles, 4, 4);

	/*
	|--------------------------------------------------------------------------
	| Number Sprites
	|--------------------------------------------------------------------------
	 */
	public static Sprite number_0 = new Sprite(DEFAULT_SIZE, 0, 2, SpriteSheet.tiles, 16, 16);
	public static Sprite number_1 = new Sprite(DEFAULT_SIZE, 1, 2, SpriteSheet.tiles, 16, 16);
	public static Sprite number_2 = new Sprite(DEFAULT_SIZE, 2, 2, SpriteSheet.tiles, 16, 16);
	public static Sprite number_3 = new Sprite(DEFAULT_SIZE, 3, 2, SpriteSheet.tiles, 16, 16);
	public static Sprite number_4 = new Sprite(DEFAULT_SIZE, 0, 3, SpriteSheet.tiles, 16, 16);
	public static Sprite number_5 = new Sprite(DEFAULT_SIZE, 1, 3, SpriteSheet.tiles, 16, 16);
	public static Sprite number_6 = new Sprite(DEFAULT_SIZE, 2, 3, SpriteSheet.tiles, 16, 16);
	public static Sprite number_7 = new Sprite(DEFAULT_SIZE, 3, 3, SpriteSheet.tiles, 16, 16);


	public Sprite(int size, int x, int y, SpriteSheet sheet, int rw, int rh) {
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		_x = x * SIZE;
		_y = y * SIZE;
		_sheet = sheet;
		_realWidth = rw;
		_realHeight = rh;
		load();
	}
	
	public Sprite(int size, int color) {
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		setColor(color);
	}
	
	private void setColor(int color) {
		for (int i = 0; i < _pixels.length; i++) {
			_pixels[i] = color;
		}
	}

	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				_pixels[x + y * SIZE] = _sheet._pixels[(x + _x) + (y + _y) * _sheet.SIZE];
			}
		}
	}
	
	public static Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2, int animate, int time) {
		int calc = animate % time;
		int diff = time / 3;
		
		if(calc < diff) {
			return normal;
		}
			
		if(calc < diff * 2) {
			return x1;
		}
			
		return x2;
	}
	
	public static Sprite movingSprite(Sprite x1, Sprite x2, int animate, int time) {
		int diff = time / 2;
		return (animate % time > diff) ? x1 : x2; 
	}
	
	public int getSize() {
		return SIZE;
	}

	public int getPixel(int i) {
		return _pixels[i];
	}

	public int get_realHeight() {
		return _realHeight;
	}

	public int get_realWidth() {
		return _realWidth;
	}

	public Image getFxImage() {
        WritableImage wr = new WritableImage(SIZE, SIZE);
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if ( _pixels[x + y * SIZE] == TRANSPARENT_COLOR) {
                    pw.setArgb(x, y, 0);
                }
                else {
                    pw.setArgb(x, y, _pixels[x + y * SIZE]);
                }
            }
        }
        Image input = new ImageView(wr).getImage();
        return resample(input, SCALED_SIZE / DEFAULT_SIZE);
    }

	private Image resample(Image input, int scaleFactor) {
		final int W = (int) input.getWidth();
		final int H = (int) input.getHeight();
		final int S = scaleFactor;

		WritableImage output = new WritableImage(
				W * S,
				H * S
		);

		PixelReader reader = input.getPixelReader();
		PixelWriter writer = output.getPixelWriter();

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				final int argb = reader.getArgb(x, y);
				for (int dy = 0; dy < S; dy++) {
					for (int dx = 0; dx < S; dx++) {
						writer.setArgb(x * S + dx, y * S + dy, argb);
					}
				}
			}
		}

		return output;
	}
}

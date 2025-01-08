# EcoEngine
A Compose Multiplatform based game engine. 

## What is EcoEngine?

This is a lightweight Compose Multiplatform-based game engine.
EcoEngine is inspired by game engines such as Flame and cocos2d-x.

It provides you with a simple yet effective game loop implementation, and the necessary functionalities that you might
need in a game like in Flame.

## Installation

Add dependency

## Usage

To run EcoEngine you need use the EcoWidget, which is just another widget that can live anywhere in your widget tree.
You can use it as the root widget of your app, or as a child of another widget.

Here is a simple example of how to use the EcoWidget:

```Kotlin
@Composable
fun App() {
    MaterialTheme {
        Box(Modifier.fillMaxSize()) {
            val scene = remember { TestScene() }
            EcoWidget(scene)
        }
    }
}
```

Scene is the center of the engine. First, create your scene:

```Kotlin
class YourScene : Scene() {

    override suspend fun onLoad() {
        super.onLoad()
        // add here your components
    }
}
```

EcoEngine provide the concept of the Flame Component System (FCS), which is a way of organizing game objects to
make them easy to manage.
All components inherit from the Component class and can have other Components as children. This is the base of FCS.
Children can be added either with the `add(child: Component)` method.

### PositionComponent

This class represents a positioned object on the screen, being a floating rectangle, a rotating sprite, or anything else
with position and size. It can also represent a group of positioned components if children are added to it.

The base of the PositionComponent is that it has a position, size, scale, angle and anchor which transforms how the
component is rendered.

## Example

Here is an example of a small scene:
```Kotlin
class TestScene : Scene() {

    override suspend fun onLoad() {
        super.onLoad()
        EcoEngine.imageManager.addRes { path -> Res.readBytes(path) }

        val positionComponent = PositionComponent()
        positionComponent.position = Offset(100f, 100f)

        val sprite1 = Sprite.load("files/img.png")
        val component1 = SpriteComponent(sprite1)
        component1.position = Offset(0f, 0f)
        component1.size = Size(100f, 100f)
        positionComponent.add(component1)

        val sprite2 = Sprite.load("files/img2.png")
        val component2 = SpriteComponent(sprite2)
        component2.position = Offset(100f, 100f)
        component2.size = Size(100f, 100f)
        positionComponent.add(component2)

        val sprite3 = Sprite.load("files/img3.png")
        val component3 = SpriteComponent(sprite3)
        component3.position = Offset(200f, 200f)
        component3.size = Size(100f, 100f)
        positionComponent.add(component3)

        add(positionComponent)
    }
}
```

## What tasks are relevant?

1. Camera
2. Gestures
3. Text rendering
4. Sprite animation

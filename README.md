# ComposeButtonFramework 🎛️

**A native button UI framework for Jetpack Compose, built entirely from scratch.**

ComposeButtonFramework is a lightweight, educational rendering framework that demonstrates how a
declarative UI toolkit like Jetpack Compose works *under the hood*. Instead of relying on
Compose's built-in `Button`, Material components, or the Compose UI layout system, ComposeButtonFramework
implements its **own node tree, measure/layout/draw pipeline, modifiers, and Canvas-based
rendering** — then bridges it back into a real Compose `@Composable` API.

The flagship widget is a fully custom, **natively drawn `Button`** with support for filled &
outlined styles, elevation/shadows, corner radius, borders, disabled states, press animations,
and content alignment — all rendered directly onto an Android `Canvas`.

---

## ✨ Features

- 🧱 **Built from scratch** — custom node tree, layout engine, and renderer. No Compose UI layout.
- 🎨 **Native Canvas rendering** — every pixel is drawn with `android.graphics.Canvas` + `Paint`.
- 🖲️ **Custom `Button` widget** with:
  - `Filled` and `Outlined` styles
  - Corner radius, elevation (soft shadows), border width & color
  - Disabled state colors (background / border / text)
  - Press feedback (scale + alpha animation)
  - Content alignment (`Start`, `Center`, `End`)
- 📐 **Custom modifier system** — `Modifier.size()`, `fillMaxWidth()`, `padding`, etc.
- 🔌 **Seamless Compose interop** — exposed as ordinary `@Composable` functions.

---

## 🏗️ Architecture Overview

MiniCompose is intentionally a *mini* reimplementation of the ideas behind Jetpack Compose.
It has three layers:

```
┌─────────────────────────────────────────────┐
│  1. Compose API layer (@Composable)          │
│     MiniCompose { Button(...) }              │
├─────────────────────────────────────────────┤
│  2. Runtime layer                            │
│     Composer  →  builds a tree of LayoutNodes│
├─────────────────────────────────────────────┤
│  3. Native rendering layer                   │
│     Renderer  →  measure / layout / draw     │
│     onto an Android View's Canvas            │
└─────────────────────────────────────────────┘
```

### Core building blocks

| Component | Responsibility |
|-----------|----------------|
| `MiniCompose()` | Entry-point composable. Hosts the framework inside a real Compose tree. |
| `Composer` | Collects emitted nodes and builds the `RootNode` tree. |
| `LayoutNode` | Base class for every UI element. Defines `measure()`, `layout()`, `draw()`. |
| `Renderer` | Runs the measure → layout → draw pipeline over the node tree. |
| `MiniComposeView` | A custom `android.view.View` that owns the `Renderer` and drives it via `onMeasure`/`onDraw`. |
| `Button` / `ButtonNode` | The composable API and its native rendering node. |
| `Modifier` | Custom chainable modifier system for sizing & padding. |

---

## 🔗 How the Button UI connects to Compose

This is the most important part of MiniCompose: it looks and feels like Compose, but every
button is drawn by hand. Here's the full flow, step by step.

### 1. You write normal Compose code

```kotlin
MiniCompose {
    Button(
        text = "Click Me",
        onClick = { /* ... */ }
    )
}
```

### 2. `MiniCompose` provides a `Composer` through a CompositionLocal

`MiniCompose()` is a real `@Composable`. It creates a `Composer`, publishes it via a
`staticCompositionLocalOf` (`LocalComposer`), and starts a fresh composition:

```kotlin
@Composable
fun MiniCompose(modifier: Modifier, content: @Composable Composer.() -> Unit) {
    val composer = remember { Composer() }

    CompositionLocalProvider(LocalComposer provides composer) {
        composer.beginComposition()   // clears the node tree
        composer.content()            // runs your Button {} calls
    }

    AndroidView(                       // ← the bridge into the native View
        factory = { context -> MiniComposeView(context) },
        update  = { view -> view.setRoot(composer.build()) }
    )
}
```

### 3. `Button` is a composable that *emits* a node instead of drawing UI

The `Button` composable doesn't render anything itself. It grabs the current `Composer` from
the CompositionLocal and **emits a `ButtonNode`** into the tree:

```kotlin
@Composable
fun Button(text: String, onClick: () -> Unit = {}, /* ...styling params... */) {
    LocalComposer.current.emit(
        ButtonNode(text = text, onClick = onClick, /* ... */)
    )
}
```

So the Compose composition's only job is to **describe** the UI as a tree of `LayoutNode`s.

### 4. `AndroidView` bridges Compose → native View

Compose can't draw MiniCompose's custom nodes on its own, so we host a plain Android `View`
(`MiniComposeView`) inside the Compose tree using `AndroidView`. On every recomposition, `update`
hands the freshly built `RootNode` tree to the view via `setRoot(...)`.

### 5. The native View runs the measure → layout → draw pipeline

`MiniComposeView` owns a `Renderer` and drives it through the standard Android View lifecycle:

- **`onMeasure`** → `renderer.measure(...)` recursively calls `measure()` on every node
  (e.g. `ButtonNode` measures its text + padding to compute its size).
- **layout** → `RootNode.layout()` and `ButtonNode.layout()` compute x/y positions
  (including content alignment inside the button).
- **`onDraw`** → `renderer.draw(canvas)` walks the tree and each node paints itself.
  `ButtonNode.draw()` draws the shadow, the rounded rect (filled or stroked), then its `TextNode`.

```kotlin
class MiniComposeView(...) : View(...) {
    private val renderer = Renderer()

    override fun onMeasure(w: Int, h: Int) {
        renderer.measure(width = MeasureSpec.getSize(w).toFloat(), height = Float.MAX_VALUE)
        setMeasuredDimension(renderer.measuredWidth.toInt(), renderer.measuredHeight.toInt())
    }

    override fun onDraw(canvas: Canvas) = renderer.draw(canvas)
}
```

### Putting it together

```
Button(...)  ──emit──▶  ButtonNode  ──added to──▶  Composer's RootNode tree
                                                        │
                            AndroidView.update ─────────┘
                                                        ▼
                                              MiniComposeView.setRoot()
                                                        │
                          ┌─────────────────────────────┼─────────────────────────────┐
                          ▼                              ▼                              ▼
                    onMeasure()                      layout()                       onDraw()
                 node.measure(...)              node positions x/y           node.draw(canvas)
                                                                        (shadow + rounded rect + text)
```

**In short:** Compose is used purely as a *declaration and lifecycle host*. The actual button
UI — its shape, shadow, colors, text and animations — is rendered natively onto a `Canvas` by
MiniCompose's own engine, and stitched back into Compose through `AndroidView`.

---

## 🚀 Usage

### Add the module

The library lives in the `:compose-button-framework` Gradle module. Include it in your app:

```kotlin
// app/setting.gradle.kts
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}

//app/build.gradle.kts
dependencies {
	        implementation 'com.github.vedprakashsah1998:ComposeButtonFramework:1.0.3'
	}
```

### Basic button

```kotlin
import com.infinity8.mini_compose.runtime.MiniCompose
import com.infinity8.mini_compose.widget.Button

setContent {
    MiniCompose {
        Button(
            text = "Get Started",
            onClick = { println("Clicked!") }
        )
    }
}
```

### Styled button

```kotlin
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.infinity8.mini_compose.modifier.Modifier
import com.infinity8.mini_compose.node.ButtonStyle

Button(
    text = "Outlined",
    modifier = Modifier.fillMaxWidth(),
    style = ButtonStyle.Outlined,
    borderWidth = 2.dp,
    borderColor = "#007AFF".toColorInt(),
    cornerRadius = 20.dp,
    elevation = 4.dp,
    enabled = true,
    onClick = { /* ... */ }
)
```

### `Button` parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| `text` | `String` | Button label. |
| `modifier` | `ModifierChain` | Size/padding via the custom `Modifier`. |
| `backgroundColor` / `textColor` | `Int` | Colors for the enabled state. |
| `enabled` | `Boolean` | Toggles enabled/disabled visuals. |
| `contentPadding` | `PaddingValues` | Inner padding around the text. |
| `textSize` / `cornerRadius` / `elevation` | `Dp` | Text size, corner rounding, shadow depth. |
| `style` | `ButtonStyle` | `Filled` or `Outlined`. |
| `borderWidth` / `borderColor` | `Dp` / `Int` | Outline styling. |
| `disabled*Color` | `Int` | Colors used when `enabled = false`. |
| `contentAlignment` | `Alignment` | `Start`, `Center`, or `End`. |
| `onClick` | `() -> Unit` | Click callback. |

---

## 🧩 Project structure

```
mini-compose/
└── src/main/java/com/infinity8/mini_compose/
    ├── runtime/     # MiniCompose(), Composer, LocalComposer
    ├── node/        # LayoutNode, RootNode, ButtonNode, TextNode
    ├── render/      # Renderer (measure/layout/draw pipeline)
    ├── ui/          # MiniComposeView (custom Android View) + unit/Dp
    ├── modifier/    # Modifier, SizeModifier, PaddingModifier
    ├── layout/      # Constraints, MeasureResult, PaddingValues
    ├── extension/   # Size & padding resolution helpers
    └── widget/      # Button composable API
```

---

## 🎯 Why this project?

MiniCompose is an educational deep-dive into **how declarative UI frameworks actually render**.
It answers questions like:

- How does a `@Composable` turn into pixels on screen?
- What is the measure → layout → draw pipeline?
- How does Compose interop with the classic Android `View` system?

By building a native button framework from scratch, this project makes those internals concrete.

---

## 📋 Requirements

- `minSdk` 24
- Jetpack Compose (BOM-managed)
- Kotlin

---



## 📄 License

Add your license here (e.g. MIT / Apache-2.0).

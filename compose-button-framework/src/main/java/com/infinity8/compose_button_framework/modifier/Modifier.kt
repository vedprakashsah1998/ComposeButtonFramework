package com.infinity8.compose_button_framework.modifier

import androidx.compose.ui.unit.Dp


interface ModifierChain {

    fun then(element: ModifierElement): ModifierChain

    fun forEach(action: (ModifierElement) -> Unit)

    fun <T : ModifierElement> find(clazz: Class<T>): T?
}

object Modifier : ModifierImpl() {

    fun size(size: Dp): ModifierChain =
        then(
            SizeModifier(
                width = SizeValue.Fixed(size),
                height = SizeValue.Fixed(size)
            )
        )

    fun size(
        width: Dp,
        height: Dp
    ): ModifierChain =
        then(
            SizeModifier(
                width = SizeValue.Fixed(width),
                height = SizeValue.Fixed(height)
            )
        )

    fun size(
        width: SizeValue,
        height: SizeValue = width
    ): ModifierChain =
        then(
            SizeModifier(
                width = width,
                height = height
            )
        )

    fun width(width: Dp): ModifierChain =
        size(width = SizeValue.Fixed(width), height = SizeValue.WrapContent)

    fun height(height: Dp): ModifierChain =
        size(width = SizeValue.WrapContent, height = SizeValue.Fixed(height))

    fun fillMaxWidth(): ModifierChain =
        size(width = SizeValue.FillMax, height = SizeValue.WrapContent)

    fun fillMaxHeight(): ModifierChain =
        size(width = SizeValue.WrapContent, height = SizeValue.FillMax)

    fun fillMaxSize(): ModifierChain =
        size(SizeValue.FillMax)

    fun wrapContentWidth(): ModifierChain =
        size(width = SizeValue.WrapContent, height = SizeValue.WrapContent)

    fun wrapContentHeight(): ModifierChain =
        size(width = SizeValue.WrapContent, height = SizeValue.WrapContent)

    fun wrapContentSize(): ModifierChain =
        size(SizeValue.WrapContent)
}

open class ModifierImpl(
    protected val elements: List<ModifierElement> = emptyList()
) : ModifierChain {

    override fun then(element: ModifierElement): ModifierChain =
        ModifierImpl(elements + element)

    override fun forEach(action: (ModifierElement) -> Unit) {
        elements.forEach(action)
    }

    override fun <T : ModifierElement> find(clazz: Class<T>): T? {
        return elements.lastOrNull(clazz::isInstance)?.let(clazz::cast)
    }
}
package com.infinity8.minicompose.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.infinity8.compose_button_framework.extension.height
import com.infinity8.compose_button_framework.extension.padding
import com.infinity8.compose_button_framework.node.ButtonStyle
import com.infinity8.compose_button_framework.widget.ComposeButton
import com.infinity8.minicompose.R

@Composable
fun LoginScreen() {
    WelcomeScreen()


}


@Composable
fun WelcomeLogo() {
    Image(
        painter = painterResource(R.drawable.mycoke_logo),
        contentDescription = "welcome logo",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(dimensionResource(R.dimen.size_100))
            .height(dimensionResource(R.dimen.size_50))
    )
}

@Composable
fun GetTextView(
    modifier: Modifier = Modifier,
    text: String = "",
    fontFamily: FontFamily = appFontFamily,
    fontWeight: FontWeight = FontWeight.Normal,
    fontStyle: FontStyle = FontStyle.Normal,
    fontSize: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = TextAlign.Start,
    textColor: Color = colorResource(id = R.color.mc_text_high_contrast),
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration = TextDecoration.None,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        color = textColor,
        textAlign = textAlign,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        lineHeight = lineHeight,
        fontStyle = fontStyle,
        textDecoration = textDecoration,
        overflow = overflow,
        maxLines = maxLines,
        style = style,
        onTextLayout = onTextLayout
    )
}

@Composable
fun WelcomeTitle() {
    GetTextView(
        fontFamily = appFontHeadLine,
        modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_24)),
        text = stringResource(R.string.dashboard_welcome_to_my_coke_title),
        textColor = colorResource(R.color.black_light),
        fontSize = dimensionResource(R.dimen.font_size_27).value.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun WelcomeSubtitle() {
    GetTextView(
        fontFamily = appFontFamily,
        modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_16)),
        text = stringResource(R.string.welcome_subtitle_lbl),
        textColor = colorResource(R.color.black_light),
        fontSize = dimensionResource(R.dimen.font_size_14).value.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun SignInButtons() {
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_16)))

    ComposeButton(
        text = stringResource(R.string.sign_in_as_guest_btn),
        modifier = com.infinity8.compose_button_framework.modifier.Modifier
            .fillMaxWidth().height(dimensionResource(R.dimen.size_48)),
        style = ButtonStyle.Outlined,
        borderWidth = dimensionResource(R.dimen.size_2),
        borderColor = com.infinity8.compose_button_framework.node.colorResource(R.color.black),
        textColor = com.infinity8.compose_button_framework.node.colorResource(R.color.black),
        cornerRadius = dimensionResource(R.dimen.padding_8),
        fontWeight = com.infinity8.compose_button_framework.FontWeight.Bold,
        onClick = {}
    )

    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_16)))

    ComposeButton(
        text = stringResource(R.string.sign_in_title),
        modifier = com.infinity8.compose_button_framework.modifier.Modifier
            .fillMaxWidth().height(dimensionResource(R.dimen.size_48))
            ,
        backgroundColor = com.infinity8.compose_button_framework.node.colorResource(R.color.black),
        cornerRadius = dimensionResource(R.dimen.size_5),
        textColor = com.infinity8.compose_button_framework.node.colorResource(R.color.white),
        elevation = 8.dp,
        fontFamily = com.infinity8.compose_button_framework.FontFamily.Resource(R.font.tccc_unitytext_bold),
        fontWeight = com.infinity8.compose_button_framework.FontWeight.Bold,
        onClick = {}
    )

    BaseDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.padding_24)),
        thickness = dimensionResource(R.dimen.margin_1)
    )
}

@Composable
fun BaseDivider(modifier: Modifier, thickness: Dp) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = colorResource(R.color.divider_color)
    )
}

@Composable
fun SignUpSection() {
    GetTextView(
        fontFamily = appFontHeadLine,
        modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_24)),
        text = stringResource(R.string.want_to_sign_up_lbl),
        textColor = colorResource(R.color.black_light),
        fontSize = dimensionResource(R.dimen.font_size_18).value.sp,
        fontWeight = FontWeight.Bold
    )

    GetTextView(
        fontFamily = appFontFamily,
        modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_8)),
        text = stringResource(R.string.signup_body_lbl), // moved to strings.xml
        textColor = colorResource(R.color.black_light),
        fontSize = dimensionResource(R.dimen.font_size_14).value.sp,
        fontWeight = FontWeight.SemiBold
    )

    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_24)))
    ComposeButton(
        text = stringResource(R.string.sign_up_btn),
        modifier = com.infinity8.compose_button_framework.modifier.Modifier
            .fillMaxWidth().height(dimensionResource(R.dimen.size_48)),
        style = ButtonStyle.Outlined,
        borderWidth = dimensionResource(R.dimen.size_2),
        borderColor = com.infinity8.compose_button_framework.node.colorResource(R.color.black),
        textColor = com.infinity8.compose_button_framework.node.colorResource(R.color.black),
        cornerRadius = dimensionResource(R.dimen.padding_8),
        fontWeight = com.infinity8.compose_button_framework.FontWeight.Bold,
        onClick = {}
    )
}

@Composable
fun HelpSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.padding_56)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GetTextView(
            fontFamily = appFontFamily,
            text = stringResource(R.string.having_trouble_lbl),
            textColor = colorResource(R.color.black_light),
            fontSize = dimensionResource(R.dimen.font_size_14).value.sp,
            fontWeight = FontWeight.SemiBold
        )

        GetTextView(
            fontFamily = appFontFamily,
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_16))
                .clickable { },
            textDecoration = TextDecoration.Underline,
            text = stringResource(R.string.get_help_signing_in_lbl),
            textColor = colorResource(R.color.black_light),
            fontSize = dimensionResource(R.dimen.font_size_12).value.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun WelcomeScreen(
) {
    MCAppTheme {
        Scaffold(
            topBar = {},
            containerColor = colorResource(R.color.white),
            content = { padding ->

                Column(
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(R.dimen.size_80),
                            start = dimensionResource(R.dimen.padding_16),
                            end = dimensionResource(R.dimen.padding_16)
                        )
                        .verticalScroll(rememberScrollState())
                        .padding(padding)
                ) {
                    WelcomeLogo()
                    WelcomeTitle()
                    WelcomeSubtitle()

                    SignInButtons()

                    SignUpSection()
                    HelpSection()


                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(

    )
}
package io.github.muqhc.skyguifx.util

enum class AlignmentLinear {
    Start, Middle, End
}

enum class Alignment(val horizontal: AlignmentLinear, val vertical: AlignmentLinear) {
    TopLeft(AlignmentLinear.Start,AlignmentLinear.Start),
    TopCenter(AlignmentLinear.Middle,AlignmentLinear.Start),
    TopRight(AlignmentLinear.End,AlignmentLinear.Start),

    MiddleLeft(AlignmentLinear.Start,AlignmentLinear.Middle),
    MiddleCenter(AlignmentLinear.Middle,AlignmentLinear.Middle),
    MiddleRight(AlignmentLinear.End,AlignmentLinear.Middle),

    BottomLeft(AlignmentLinear.Start,AlignmentLinear.End),
    BottomCenter(AlignmentLinear.Middle,AlignmentLinear.End),
    BottomRight(AlignmentLinear.End,AlignmentLinear.End)
}
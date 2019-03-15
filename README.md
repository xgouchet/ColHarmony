# ColHarmony

> ColHarmony is a Kotlin tool used to bring harmonious color scheme to any picture

[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://opensource.org/licenses/MIT)
[![Kotlin 1.1.2-4](https://img.shields.io/badge/Kotlin-1.3.0-blue.svg)](http://kotlinlang.org)

## Description

*ColHarmony* uses the theory described in the [‘Color Harmonization’ (Cohen-Or et al. 2006)](https://igl.ethz.ch/projects/color-harmonization/harmonization.pdf) to compute the best way to modify an input color's picture to maximize the color harmony

## Usage

This work is still in beta. Use at your own risks.

### Basic

The basic command line will find the best pattern and apply it on the image, creating a new image along side named with the pattern and resolution.

```
colharmony -i /path/to/input/image.jpg
```

### Resolution

When a pixel's color is out of harmony, the resolution decides how to change this pixel's color : 

 - `--desaturate` : a color out of harmony is fully desaturated
 - `--fade` : colors out of harmony are desaturated according to how far they are from harmony
 - `--shift` : colors out of harmony are shifted to the nearest harmonious hue
 - `--scale` : colors are scaled so that all colors are in harmony

### Keep Hues

When running *ColHarmony* on an image, you might want to keep some hues intact (eg: skin color, a specific object, …). You can do so by providing the hue you want to keep (you can keep more than one). 

```
colharmony -i /path/to/input/image.jpg --keep-hue 0 --keep-hue 140
```

A basic list of hue values : 

 - 0 : Red
 - 60 : Yellow
 - 120 : Green
 - 180 : Cyan
 - 240 : Blue
 - 300 : Purple

## Contributions

Contribution is fully welcome, be it in the form of issues, feature requests or pull requests.

## Meta

Xavier F. Gouchet – [@xgouchet](https://twitter.com/xgouchet)

Distributed under the MIT license. See [LICENSE.md](LICENSE.md) for more information.

[https://github.com/xgouchet/ColHarmony](https://github.com/xgouchet/ColHarmony)

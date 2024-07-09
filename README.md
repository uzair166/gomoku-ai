# Gomoku Player

This repository contains my solution for the Artificial Intelligence Assignment 1: Gomoku Player, for my BSc computer science degree.

## Description

This project implements a Java class to play the game Gomoku (five-in-a-row) on an 8x8 board using alpha-beta search. The player class extends the abstract `GomokuPlayer` class and overrides the `chooseMove()` method to determine the best move given the current board state.

## Features

- Implements alpha-beta search algorithm
- Complies with the 10-second time limit per move
- Plays on an 8x8 board
- Handles both black and white stones

## Usage

To use this player, ensure you have the `GomokuReferee` class in the same directory. You can then run games or tournaments using the referee's interface.

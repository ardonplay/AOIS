use crate::float_operations::floating_operations::{binary_float_sum, decimal_to_float};
use crate::operations::binary_operations::*;

mod float_operations;
mod operations;

fn run(first: i32, second: i32) {
    println!("sum");
    let sum = [
        to_decimal(binary_sum(to_binary(first, 32), to_binary(second, 32)), 32),
        to_decimal(binary_sum(to_binary(-first, 32), to_binary(second, 32)), 32),
        to_decimal(binary_sum(to_binary(first, 32), to_binary(-second, 32)), 32),
        to_decimal(
            binary_sum(to_binary(-first, 32), to_binary(-second, 32)),
            32,
        ),
    ];
    for x in &sum {
        println!("{x}, {}", to_binary(*x, 32));
    }

    println!("\nmuliplex");
    let mult = [
        to_decimal(multiplex(to_binary(first, 32), to_binary(second, 32)), 32),
        to_decimal(multiplex(to_binary(-first, 32), to_binary(second, 32)), 32),
        to_decimal(multiplex(to_binary(first, 32), to_binary(-second, 32)), 32),
        to_decimal(multiplex(to_binary(-first, 32), to_binary(-second, 32)), 32),
    ];
    for x in &mult {
        println!("{x}, {}", to_binary(*x, 32));
    }

    println!("\ndivide");
    let div = [
        to_decimal(divide(to_binary(first, 32), to_binary(second, 32)), 32),
        to_decimal(divide(to_binary(-first, 32), to_binary(second, 32)), 32),
        to_decimal(divide(to_binary(first, 32), to_binary(-second, 32)), 32),
        to_decimal(divide(to_binary(-first, 32), to_binary(-second, 32)), 32),
    ];
    for x in &div {
        println!("{x}, {}", to_binary(*x, 32));
    }

    println!("\ndivide_reverse");
    let divide_reverse = [
        to_decimal(divide(to_binary(second, 32), to_binary(first, 32)), 32),
        to_decimal(divide(to_binary(-second, 32), to_binary(first, 32)), 32),
        to_decimal(divide(to_binary(second, 32), to_binary(-first, 32)), 32),
        to_decimal(divide(to_binary(-second, 32), to_binary(-first, 32)), 32),
    ];
    for x in &divide_reverse {
        println!("{x}, {}", to_binary(*x, 32));
    }

    println!("\nfloat sum");

    let first_float: f32 = 0.5f32;
    let second_float: f32 = 18.125f32;

    binary_float_sum(
        decimal_to_float(first_float),
        decimal_to_float(second_float),
    );
    binary_float_sum(
        decimal_to_float(-first_float),
        decimal_to_float(second_float),
    );
    binary_float_sum(
        decimal_to_float(first_float),
        decimal_to_float(-second_float),
    );
    binary_float_sum(
        decimal_to_float(-first_float),
        decimal_to_float(-second_float),
    );
}

fn main() {
    run(15, 2)
}

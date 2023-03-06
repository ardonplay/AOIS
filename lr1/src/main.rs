use crate::constants::BINARY_SIZE;
use crate::float_operations::floating_operations::{binary_float_sum, decimal_to_float};
use crate::operations::binary_operations::*;

mod float_operations;
mod operations;
mod constants;

fn run(first: i32, second: i32) {
    println!("sum");
    let sum = [
        to_decimal(binary_sum(to_binary(first, BINARY_SIZE), to_binary(second,BINARY_SIZE)),BINARY_SIZE),
        to_decimal(binary_sum(to_binary(-first,BINARY_SIZE), to_binary(second,BINARY_SIZE)),BINARY_SIZE),
        to_decimal(binary_sum(to_binary(first,BINARY_SIZE), to_binary(-second,BINARY_SIZE)),BINARY_SIZE),
        to_decimal(
            binary_sum(to_binary(-first,BINARY_SIZE), to_binary(-second,BINARY_SIZE)),
           BINARY_SIZE,
        ),
    ];
    for x in &sum {
        println!("{x}, {}", to_binary(*x,BINARY_SIZE));
    }

    println!("\nmuliplex");
    let mult = [
        to_decimal(multiplex(to_binary(first,BINARY_SIZE), to_binary(second,BINARY_SIZE)),BINARY_SIZE),
        to_decimal(multiplex(to_binary(-first,BINARY_SIZE), to_binary(second,BINARY_SIZE)),BINARY_SIZE),
        to_decimal(multiplex(to_binary(first,BINARY_SIZE), to_binary(-second,BINARY_SIZE)),BINARY_SIZE),
        to_decimal(multiplex(to_binary(-first,BINARY_SIZE), to_binary(-second,BINARY_SIZE)),BINARY_SIZE),
    ];
    for x in &mult {
        println!("{x}, {}", to_binary(*x,BINARY_SIZE));
    }

    println!("\ndivide");
    let div = [
        to_decimal(divide(to_binary(first,BINARY_SIZE), to_binary(second,BINARY_SIZE)),BINARY_SIZE),
        to_decimal(divide(to_binary(-first,BINARY_SIZE), to_binary(second,BINARY_SIZE)),BINARY_SIZE),
        to_decimal(divide(to_binary(first,BINARY_SIZE), to_binary(-second,BINARY_SIZE)),BINARY_SIZE),
        to_decimal(divide(to_binary(-first,BINARY_SIZE), to_binary(-second,BINARY_SIZE)),BINARY_SIZE),
    ];
    for x in &div {
        println!("{x}, {}", to_binary(*x,BINARY_SIZE));
    }

    println!("\ndivide_reverse");
    let divide_reverse = [
        to_decimal(divide(to_binary(second,BINARY_SIZE), to_binary(first,BINARY_SIZE)),BINARY_SIZE),
        to_decimal(divide(to_binary(-second,BINARY_SIZE), to_binary(first,BINARY_SIZE)),BINARY_SIZE),
        to_decimal(divide(to_binary(second,BINARY_SIZE), to_binary(-first,BINARY_SIZE)),BINARY_SIZE),
        to_decimal(divide(to_binary(-second,BINARY_SIZE), to_binary(-first,BINARY_SIZE)),BINARY_SIZE),
    ];
    for x in &divide_reverse {
        println!("{x}, {}", to_binary(*x,BINARY_SIZE));
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

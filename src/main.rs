use crate::float_operations::floating_operations::{binary_float_sum, decimal_to_float};
use crate::operations::binary_operations::{*};

mod operations;
mod float_operations;

fn run(first: i32, second: i32) {
    let mut sum = [
        to_decimal(binary_sum(to_binary(first, 32), to_binary(second, 32)), 32),
        to_decimal(binary_sum(to_binary(-first, 32), to_binary(second, 32)), 32),
        to_decimal(binary_sum(to_binary(first, 32), to_binary(-second, 32)), 32),
        to_decimal(binary_sum(to_binary(-first, 32), to_binary(-second, 32)), 32)];
    for x in &sum {
        println!("{x}");
    }

    let mult = [
        to_decimal(multiplex(to_binary(first, 32), to_binary(second, 32)), 32),
        to_decimal(multiplex(to_binary(-first, 32), to_binary(second, 32)), 32),
        to_decimal(multiplex(to_binary(first, 32), to_binary(-second, 32)), 32),
        to_decimal(multiplex(to_binary(-first, 32), to_binary(-second, 32)), 32)];
    for x in &mult {
        println!("{x}");
    }

    let div = [
        to_decimal(divide(to_binary(first, 32), to_binary(second, 32)), 32),
        to_decimal(divide(to_binary(-first, 32), to_binary(second, 32)), 32),
        to_decimal(divide(to_binary(first, 32), to_binary(-second, 32)), 32),
        to_decimal(divide(to_binary(-first, 32), to_binary(-second, 32)), 32)];
    for x in &div {
        println!("{x}");
    }
    let div = [
        to_decimal(divide(to_binary(second, 32), to_binary(first, 32)), 32),
        to_decimal(divide(to_binary(-second, 32), to_binary(first, 32)), 32),
        to_decimal(divide(to_binary(second, 32), to_binary(-first, 32)), 32),
        to_decimal(divide(to_binary(-second, 32), to_binary(-first, 32)), 32)];
    for x in &div {
        println!("{x}");
    }

    let first_float: f32 = 1.4f32;
    let second_float: f32 = 2.5f32;
    binary_float_sum(decimal_to_float(first_float), decimal_to_float(second_float));
    binary_float_sum(decimal_to_float(-first_float), decimal_to_float(second_float));
    binary_float_sum(decimal_to_float(first_float), decimal_to_float(-second_float));
    binary_float_sum(decimal_to_float(-first_float), decimal_to_float(-second_float));
}

fn main() {
    run(14, 25)
}

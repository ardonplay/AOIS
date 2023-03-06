pub mod floating_operations {
    use std::cmp::Ordering;
    use crate::constants::{FLOAT_INDEX_SIZE, FLOAT_MANTISSA_SIZE};

    use crate::operations::binary_operations::{binary_sum, to_binary, to_decimal};

    pub struct Float {
        sign: i8,
        mantissa: String,
        index: String,
    }

    impl Clone for Float {
        fn clone(&self) -> Float {
            Float {
                sign: self.sign.clone(),
                mantissa: self.mantissa.clone(),
                index: self.index.clone(),
            }
        }
    }

    pub fn print_float_bi_code(float: Float) {
        println!("{}|{}|{}\n", float.sign, float.index, float.mantissa);
    }

    pub fn decimal_to_float(mut decimal: f32) -> Float {
        let mut output = Float {
            mantissa: String::new(),
            index: String::new(),
            sign: 0,
        };

        let decimal_string = String::from(decimal.to_string());
        if decimal < 0 as f32 {
            output.sign = 1;
            decimal = -decimal;
        }

        let mut index = decimal_string.chars().count() - 1;

        let mut ind = 0;

        while index > 0 {
            if decimal_string.chars().nth(index).unwrap() == '.' {
                break;
            } else {
                ind += 1;
            }
            index -= 1;
        }

        let decimal_int = (decimal * f32::powf(10.0, ind as f32)) as i32;
        output.index = to_binary(ind,FLOAT_INDEX_SIZE);
        output.mantissa = to_binary(decimal_int, FLOAT_MANTISSA_SIZE);

        output
    }

    pub fn basing(mut first: Float, second: Float, first_index: i32) -> Float {
        while first.index.clone() != second.index.clone() {
            first.index = binary_sum(first.index.clone(), to_binary(1,FLOAT_INDEX_SIZE));
        }
        let mut digit_mantissa = to_decimal(first.mantissa.clone(), FLOAT_MANTISSA_SIZE);
        digit_mantissa *= i32::pow(
            10,
            (to_decimal(first.index.clone(),FLOAT_INDEX_SIZE) - first_index) as u32,
        );
        first.mantissa = to_binary(digit_mantissa, FLOAT_MANTISSA_SIZE);
        first
    }

    pub fn binary_float_sum(mut first: Float, mut second: Float) {
        let mut res = Float {
            mantissa: String::new(),
            index: String::new(),
            sign: 0,
        };

        let second_index = to_decimal(second.index.clone(),FLOAT_INDEX_SIZE);
        let first_index = to_decimal(first.index.clone(),FLOAT_INDEX_SIZE);

        match first_index.cmp(&second_index) {
            Ordering::Less => first = basing(first.clone(), second.clone(), first_index),
            Ordering::Equal => {}
            Ordering::Greater => second = basing(second.clone(), first.clone(), second_index),
        }

        if first.sign == 1 && second.sign == 1 {
            res.sign = 1;
            first.mantissa.replace_range(0..1, "1");
            second.mantissa.replace_range(0..1, "1");
        } else if first.sign == 1 {
            first.mantissa.replace_range(0..1, "1");
        } else if second.sign == 1 {
            second.mantissa.replace_range(0..1, "1");
        }

        res.index = first.index;
        res.mantissa = binary_sum(first.mantissa, second.mantissa);
        let res_output: f32 = to_decimal(res.clone().mantissa, FLOAT_MANTISSA_SIZE) as f32
            * f32::powf(10.0, (-to_decimal(res.clone().index,FLOAT_INDEX_SIZE)) as f32);
        println!("{}", res_output);
        print_float_bi_code(res);
    }
}

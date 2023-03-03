pub mod binary_operations {
    pub fn to_binary(mut input: i32, bin_size: i32) -> String {
        let mut output = String::new();

        let sign = input;

        input = input.abs();

        loop {
            if input > 0 {
                let x: u32 = (input % 2) as u32;
                let temp = x.to_string();

                output.push_str(&temp);
                input /= 2;
            } else {
                break;
            }
        }
        let space = bin_size - 1 - output.chars().count() as i32;
        if space > 0 {
            for _i in 0..space {
                output.push('0');
            }
        }

        if sign.is_negative() {
            output.push('1');
        } else if sign.is_positive() {
            output.push('0');
        }
        if sign == 0 {
            output.push('0');
        }
        output.chars().rev().collect()
    }

    pub fn to_decimal(input: String, bin_size: i32) -> i32 {
        let mut output: i32 = 0;

        let mut degree: i32 = bin_size - 2;

        let sign = input.chars().next().unwrap();

        let mut chars = input.chars();

        chars.next();

        for i in chars {
            let bit: i32 = i.to_digit(10).unwrap() as i32;

            let block = bit * i32::pow(2, degree as u32);
            output += block;
            degree -= 1;
        }
        if sign == '1' {
            output *= -1;
        }
        output
    }

    pub fn to_rev_view(input: String, bin_size: i32) -> String {
        let mut output = String::with_capacity(bin_size as usize);
        output.push(input.chars().next().unwrap());
        for i in 1..bin_size {
            if input.chars().nth(i as usize).unwrap() == '0' {
                output.push('1')
            } else if input.chars().nth(i as usize).unwrap() == '1' {
                output.push('0');
            }
        }
        output
    }

    pub fn to_ext_view(input: String, bin_size: i32) -> String {
        summator(to_rev_view(input, bin_size), to_binary(1, bin_size))
    }


    pub fn binary_sum(mut first: String, mut second: String) -> String {
        let bin_size = first.chars().count();
        if first.starts_with('1') && second.starts_with('1') {
            first = to_ext_view(first, bin_size as i32);
            second = to_ext_view(second, bin_size as i32);
        } else if first.starts_with('1') {
            first = to_ext_view(first, bin_size as i32);
        } else if second.starts_with('1') {
            second = to_ext_view(second, bin_size as i32);
        }
        let mut sum = summator(first, second);

        if sum.starts_with('1') {
            sum = from_addition_to_reverse(sum, bin_size as i32);
            sum = to_rev_view(sum, bin_size as i32)
        }

        sum
    }


    fn perceptron(first: char, second: char, trans: bool, sum: &mut String) -> bool {
        let mut trans = trans;
        if first == '0' && second == '0' {
            match trans {
                true => {
                    sum.push('1');
                    trans = false;
                }
                false => {
                    sum.push('0');
                    trans = false;
                }
            }
        } else if (first == '1' && second == '0') ||
            (first == '0' && second == '1') {
            match trans {
                true => {
                    sum.push('0');
                    trans = true;
                }
                false => {
                    sum.push('1');
                    trans = false;
                }
            }
        } else if first == '1' && second == '1' {
            match trans {
                true => {
                    sum.push('1');
                    trans = true;
                }
                false => {
                    sum.push('0');
                    trans = true;
                }
            }
        }
        trans
    }

    pub fn summator(first: String, second: String) -> String {
        let mut sum = String::new();


        let mut trans: bool = false;


        let mut index = first.chars().count();


        loop {
            if index == 0 {
                break;
            }
            let first_binary_symbol = first.chars().nth(index - 1).unwrap();
            let second_binary_symbol = second.chars().nth(index - 1).unwrap();
            trans = perceptron(first_binary_symbol, second_binary_symbol, trans, &mut sum);


            index -= 1;
        }
        sum.chars().rev().collect()
    }

    pub fn from_addition_to_reverse(input: String, bin_size: i32) -> String {
        let mut output = input.clone();
        let sign = input.chars().next().unwrap();
        if sign == '1' {
            output = summator(input.chars().collect(), to_ext_view(to_binary(-1, bin_size), bin_size));
        }
        output
    }

    pub fn get_sign(first: String, second: String) -> char {
        let mut sign: char = '0';
        if first.starts_with('0') && second.starts_with('0') {
            sign = '0';
        } else if (first.starts_with('0') && second.starts_with('1')) || (first.starts_with('1') && second.starts_with('0')) {
            sign = '1';
        } else if first.starts_with('1') && second.starts_with('1') {
            sign = '2';
        }

        sign
    }

    pub fn multiplex(first: String, second: String) -> String {
        let mut iter = to_binary(1, 32);

        let mut output = first.clone();
        let sign = get_sign(first.clone(), second.clone());
        let mut end = second;

        end.replace_range(0..1, "0");
        loop {
            if iter != end {
                output = binary_sum(output, first.clone());
            } else {
                break;
            }
            iter = summator(iter, to_binary(1, 32));
        }
        if sign == '1' {
            output.replace_range(0..1, "1");
        } else if sign == '2' {
            output.replace_range(0..1, "0");
        }
        output
    }

    pub fn divide(mut first: String, mut second: String) -> String {
        let sign = get_sign(first.clone(), second.clone());
        first.replace_range(0..1, "0");


        if second == to_binary(0, 32) {
            return to_binary(0, 32);
        }
        second.replace_range(0..1, "1");

        let mut output = first.clone();

        let mut iter = to_binary(0, 32);
        loop {
            output = binary_sum(output, second.clone());
            if !output.starts_with(first.chars().next().unwrap()) {
                break;
            }
            iter = binary_sum(iter, to_binary(1, 32));
        }

        if sign == '1' {
            iter.replace_range(0..1, "1");
        }


        iter
    }
}
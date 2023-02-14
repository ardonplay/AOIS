fn to_binary(mut input: i32) -> String {
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
    let space = 7 - output.chars().count();
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

fn to_decimal(input: String) -> i32 {
    let mut output: i32 = 0;

    let mut degree: i32 = 6;

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

fn to_ext_view(input: String) -> String {
    let mut output = String::new();
    output.push(input.chars().next().unwrap());
    for i in 1..8 {
        if input.chars().nth(i).unwrap() == '0' {
            output.push('1')
        } else if input.chars().nth(i).unwrap() == '1' {
            output.push('0');
        }
    }
    println!("{}", output);
    output
}


fn binary_sum(mut first: String, mut second: String) -> String {
    let mut sum = String::from("000000000");

    first = first.chars().rev().collect();
    second = second.chars().rev().collect();
    for i in 0..8 {
        let fist_char: char = first.chars().nth(i).unwrap();
        let second_char: char = second.chars().nth(i).unwrap();
        let sum_char = sum.chars().nth(i).unwrap();

        let num = fist_char.to_digit(10).unwrap() +
            second_char.to_digit(10).unwrap() +
            sum_char.to_digit(10).unwrap();
        println!("{},   {}",sum, num);
        match num {
            0 => sum.replace_range(i..i + 1, "0"),
            1 => sum.replace_range(i..i + 1, "1"),
            2 => {
                sum.replace_range(i..i + 1, "0");
                sum.replace_range(i + 1..i + 2, "1")
            }
            3 => {
                sum.replace_range(i..i + 1, "1");
                sum.replace_range(i + 1..i + 2, "1")
            }
            _ => {}
        }
    }
    sum.remove(8);
    sum = sum.chars().rev().collect();
    println!("{}, {}, {}", first, second, sum);
    sum
}


fn main() {
    let minsum = binary_sum(to_ext_view(to_binary(4)), to_binary(-7));
    //println!("{}",to_decimal(minsum));
    let sum = (binary_sum(minsum, to_binary(1)));
    println!("{}", to_decimal(sum));
    //println!("{}", to_decimal(to_ext_view(binary_sum(to_binary(-1), sum))));
}
